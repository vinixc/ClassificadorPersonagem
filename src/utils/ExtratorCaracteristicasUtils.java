package utils;

import static com.googlecode.javacv.cpp.opencv_core.cvSet2D;

import java.io.File;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;

import dtos.CaracteristicasHomerAndBart;

public class ExtratorCaracteristicasUtils {
	
	public static String montaHeaderArquivoARFFBartOrHomer() {
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute laranja_camisa_bart real\n";
		exportacao += "@attribute azul_calcao_bart real\n";
		exportacao += "@attribute azul_sapato_bart real\n";
		exportacao += "@attribute marrom_boca_homer real\n";
		exportacao += "@attribute azul_calca_homer real\n";
		exportacao += "@attribute cinza_sapato_homer real\n";
		exportacao += "@attribute classe {Bart, Homer}\n\n";
		exportacao += "@data\n";
		
		return exportacao;
	}
	
	public static File[] carregaArquivos(String path) {
		File diretorio = new File(path);
        return diretorio.listFiles();
	}
	
	public static File getDiretorio(String path) {
		return new File(path);
	}
	
	public static void defineClassPersonagem(File arquivo,CaracteristicasHomerAndBart.Builder builder){
        if (arquivo.getName().charAt(0) == 'b') {
        	builder.withClassePersonagem(0).withClassePersonagemString("Bart");
        } else {
        	builder.withClassePersonagem(1).withClassePersonagemString("Homer");
        }
	}
	
	public static void pintaImagemProcessadaVerdeLimao(CvScalar scalarImagemProcessada, CvMat mtx, int altura, int largura) {
		scalarImagemProcessada.setVal(0, 0);
        scalarImagemProcessada.setVal(1, 255);
        scalarImagemProcessada.setVal(2, 128);
        
        cvSet2D(mtx, altura, largura, scalarImagemProcessada);
	}
}
