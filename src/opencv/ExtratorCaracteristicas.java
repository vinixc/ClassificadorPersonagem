package opencv;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCloneImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGet2D;
import static com.googlecode.javacv.cpp.opencv_core.cvGetMat;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import dtos.CaracteristicasHomerAndBart;
import dtos.CaracteristicasHomerAndBart.Builder;
import utils.ExtratorCaracteristicasUtils;

public class ExtratorCaracteristicas {

    @SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
//        int proximaImagem;

        // Cabecalho do arquivo Weka
        String exportacao = ExtratorCaracteristicasUtils.montaHeaderArquivoARFFBartOrHomer();
        
        // Imagens para alimentar base
        File diretorio = ExtratorCaracteristicasUtils.getDiretorio("src\\imagens");
        File[] arquivos = ExtratorCaracteristicasUtils.carregaArquivos("src\\imagens");

        // Caracteristicas do Homer e Bart
        Builder builder = new CaracteristicasHomerAndBart.Builder();
        
        builder.withLaranjaCamisaBart(0)
        .withAzulCalcaoBart(0)
        .withAzulSapatoBart(0)
        .withAzulCalcaHomer(0)
        .withMarromBocaHomer(0)
        .withCinzaSapatoHomer(0);
        
        // Definicao do vetor de caracteristicas
        float[][] caracteristicas = new float[293][7];

        // Percorre todas as imagens do diretorio
        for (int i = 0; i < arquivos.length; i++) {
            // Carrega cada imagem do diretorio
            
        	IplImage imagemOriginal = cvLoadImage(diretorio.getAbsolutePath() + "\\" + arquivos[i].getName());
        	
        	builder.withImagemOriginalHeight(imagemOriginal.height());
        	builder.withImagemOriginalWidth(imagemOriginal.width());
        	
            CvSize tamanhoImagemOriginal = cvGetSize(imagemOriginal);
            
            // Imagem processada - tamanho, profundidade de cores e numero de canais de cores
            IplImage imagemProcessada = cvCreateImage(tamanhoImagemOriginal, IPL_DEPTH_8U, 3);
            imagemProcessada = cvCloneImage(imagemOriginal);

            // Definicao da classe - Homer ou Bart
            //Aprendizagem supervisionada
            ExtratorCaracteristicasUtils.defineClassPersonagem(arquivos[i], builder);

            // Matriz multi-canal
            CvMat mtx = CvMat.createHeader(imagemProcessada.height(), imagemProcessada.width());
            CvScalar scalarImagemProcessada = new CvScalar();
            cvGetMat(imagemProcessada, mtx, null, 0);

            // Varre a imagem pixel a pixel
            for (int altura = 0; altura < imagemProcessada.height(); altura++) {
                for (int largura = 0; largura < imagemProcessada.width(); largura++) {
                    
                    // Extrai o RGB de cada pixel da imagem
                    CvScalar scalarExtraiRgb = cvGet2D(imagemProcessada, altura, largura);
                    // Camisa laranja do Bart                    
                    if (builder.isLaranjaCamisaBart(scalarExtraiRgb)) { 
                    	
                    	//Pinta imagem processada com verde limao.
                        ExtratorCaracteristicasUtils.pintaImagemProcessadaVerdeLimao(scalarImagemProcessada, mtx, altura, largura);
                        
                        builder.withLaranjaCamisaBartSoma1();
                    }

                    //Calcao azul do Bart (metade de baixo da imagem)
                    if (altura > (imagemProcessada.height() / 2)) {
                        if (builder.isCalcaoAzulBart(scalarExtraiRgb)) {
                        	
                            ExtratorCaracteristicasUtils.pintaImagemProcessadaVerdeLimao(scalarImagemProcessada, mtx, altura, largura);
                            builder.withAzulCalcaoBartSoma1();
                        }
                    }

                    // Sapato do Bart (parte inferior da imagem)
                    if (altura > (imagemProcessada.height() / 2) + (imagemProcessada.height() / 3)) {
                        if (builder.isSapatoBart(scalarExtraiRgb)) {
                        	ExtratorCaracteristicasUtils.pintaImagemProcessadaVerdeLimao(scalarImagemProcessada, mtx, altura, largura);
                        	builder.withSaparoBartSoma1();
                        }
                    }

                    // Calça azul do Homer
                    if (builder.isCalcaoHomer(scalarExtraiRgb)) {
                    	ExtratorCaracteristicasUtils.pintaImagemProcessadaVerdeLimao(scalarImagemProcessada, mtx, altura, largura);
                    	builder.withCalcaoHomerSoma1();
                    }

                    // Boca do Homer (pouco mais da metade da imagem)
                    if (altura < (imagemProcessada.height() / 2) + (imagemProcessada.height() / 3)) {
                    	
                        if (builder.isBocaHomer(scalarExtraiRgb)) {
                        	ExtratorCaracteristicasUtils.pintaImagemProcessadaVerdeLimao(scalarImagemProcessada, mtx, altura, largura);
                        	builder.withBocaHomerSoma1();
                        }
                    }

                    // Sapato do Homer (parte inferior da imagem)
                    if (altura > (imagemProcessada.height() / 2) + (imagemProcessada.height() / 3)) {
                        if (builder.isSapatoHomer(scalarExtraiRgb)) {
                        	ExtratorCaracteristicasUtils.pintaImagemProcessadaVerdeLimao(scalarImagemProcessada, mtx, altura, largura);
                            builder.withSapatoHomerSoma1();
                        }
                    }
                }
            }

            // Imagem processada de acordo com as características (alteração das cores)
            imagemProcessada = new IplImage(mtx);
            
            CaracteristicasHomerAndBart caracteristicasHomerAndBart = builder.buildCaracteristicas();

            // Grava as características no vetor de características
            caracteristicas[i][0] = caracteristicasHomerAndBart.getLaranjaCamisaBartNormalize();
            caracteristicas[i][1] = caracteristicasHomerAndBart.getAzulCalcaoBartNormalize();
            caracteristicas[i][2] = caracteristicasHomerAndBart.getAzulSapatoBartNormalize();
            caracteristicas[i][3] = caracteristicasHomerAndBart.getAzulCalcaHomerNormalize();
            caracteristicas[i][4] = caracteristicasHomerAndBart.getMarromBocaHomerNormalize();
            caracteristicas[i][5] = caracteristicasHomerAndBart.getCinzaSapatoHomerNormalize();
            caracteristicas[i][6] = caracteristicasHomerAndBart.getClassePersonagem();

            System.out.println("Laranja camisa Bart: " + caracteristicas[i][0] + " - Azul calção Bart: " + caracteristicas[i][1] + " - Azul sapato Bart: " + caracteristicas[i][2] + " - Azul calça Homer: " + caracteristicas[i][3] + " - Marrom boca Homer: " + caracteristicas[i][4] + " - Preto sapato Homer: " + caracteristicas[i][5] + " - Classe: " + caracteristicas[i][6]);
            exportacao += caracteristicas[i][0] + "," + caracteristicas[i][1] + "," + caracteristicas[i][2] + "," + caracteristicas[i][3] + "," + caracteristicas[i][4] + "," + caracteristicas[i][5] + "," + caracteristicasHomerAndBart.getClassePersonagem() + "\n";

//            cvShowImage("Imagem original", imagemOriginal);
//            cvShowImage("Imagem processada", imagemProcessada);
//            proximaImagem = cvWaitKey();
        }

        // Grava o arquivo ARFF no disco
        File arquivo = new File("caracteristicas.arff");
        FileOutputStream f = new FileOutputStream(arquivo);
        f.write(exportacao.getBytes());
        f.close();
    }
}