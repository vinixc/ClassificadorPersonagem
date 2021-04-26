package dtos;

import java.io.Serializable;

import com.googlecode.javacv.cpp.opencv_core.CvScalar;

public class CaracteristicasHomerAndBart implements Serializable{
	private static final long serialVersionUID = 9208830094186674138L;
	
	private float laranjaCamisaBart;
	private float azulCalcaoBart;
	private float azulSapatoBart;
	private float azulCalcaHomer;
	private float marromBocaHomer;
	private float cinzaSapatoHomer;
	private int classePersonagem;
	private String classePersonagemString;
	private int imagemOriginalHeight;
	private int imagemOriginalWidth;
	
	public CaracteristicasHomerAndBart(Builder builder) {
		this.laranjaCamisaBart = builder.laranjaCamisaBart;
		this.azulCalcaoBart = builder.azulCalcaoBart;
		this.azulSapatoBart = builder.azulSapatoBart;
		this.azulCalcaHomer = builder.azulCalcaHomer;
		this.marromBocaHomer = builder.marromBocaHomer;
		this.cinzaSapatoHomer = builder.cinzaSapatoHomer;
		this.classePersonagem = builder.classePersonagem;
		this.classePersonagemString = builder.classePersonagemString;
		this.setImagemOriginalHeight(builder.imagemOriginalHeight);
		this.setImagemOriginalWidth(builder.imagemOriginalWidth);
	}
	
	public float getLaranjaCamisaBartNormalize() {
		return (laranjaCamisaBart / tamanhoImagem()) * 100;
	}
	public float getAzulCalcaoBartNormalize() {
		return (azulCalcaoBart / tamanhoImagem()) * 100;
	}
	public float getAzulSapatoBartNormalize() {
		return (azulSapatoBart / tamanhoImagem()) * 100;
	}
	public float getAzulCalcaHomerNormalize() {
		return (azulCalcaHomer / tamanhoImagem()) * 100;
	}
	public float getMarromBocaHomerNormalize() {
		return (marromBocaHomer / tamanhoImagem()) * 100;
	}
	public float getCinzaSapatoHomerNormalize() {
		return (cinzaSapatoHomer / tamanhoImagem()) * 100;
	}

	private int tamanhoImagem() {
		return imagemOriginalHeight * imagemOriginalWidth;
	}
	
	public float getLaranjaCamisaBart() {
		return laranjaCamisaBart;
	}
	public float getAzulCalcaoBart() {
		return azulCalcaoBart;
	}
	public float getAzulSapatoBart() {
		return azulSapatoBart;
	}
	public float getAzulCalcaHomer() {
		return azulCalcaHomer;
	}
	public float getMarromBocaHomer() {
		return marromBocaHomer;
	}
	public float getCinzaSapatoHomer() {
		return cinzaSapatoHomer;
	}
	
	public int getClassePersonagem() {
		return classePersonagem;
	}

	public String getClassePersonagemString() {
		return classePersonagemString;
	}

	public int getImagemOriginalHeight() {
		return imagemOriginalHeight;
	}

	public void setImagemOriginalHeight(int imagemOriginalHeight) {
		this.imagemOriginalHeight = imagemOriginalHeight;
	}

	public int getImagemOriginalWidth() {
		return imagemOriginalWidth;
	}

	public void setImagemOriginalWidth(int imagemOriginalWidth) {
		this.imagemOriginalWidth = imagemOriginalWidth;
	}

	public static class Builder {
		
		private float laranjaCamisaBart;
		private float azulCalcaoBart;
		private float azulSapatoBart;
		private float azulCalcaHomer;
		private float marromBocaHomer;
		private float cinzaSapatoHomer;
		private int classePersonagem;
		private String classePersonagemString;
		private int imagemOriginalHeight;
		private int imagemOriginalWidth;
		
		public static boolean isLaranjaCamisaBart(CvScalar scalarExtraiRgb) {
			double blue = scalarExtraiRgb.val(0);
			double green = scalarExtraiRgb.val(1);
			double red = scalarExtraiRgb.val(2);
			
			if (blue >= 11 && blue <= 22 &&  green >= 85 && green <= 105 &&  red >= 240 && red <= 255) {
				return true;
			}
			
			return false;
		}
		
		public static boolean isCalcaoAzulBart(CvScalar scalarExtraiRgb) {
			double blue = scalarExtraiRgb.val(0);
			double green = scalarExtraiRgb.val(1);
			double red = scalarExtraiRgb.val(2);
			
			if (blue >= 125 && blue <= 170 && green >= 0 && green <= 12 && red >= 0 && red <= 20) {
				return true;
            }

			return false;
		}
		
		public static boolean isSapatoBart(CvScalar scalarExtraiRgb) {
			double blue = scalarExtraiRgb.val(0);
			double green = scalarExtraiRgb.val(1);
			double red = scalarExtraiRgb.val(2);
			
			 if (blue >= 125 && blue <= 140 && green >= 3 && green <= 12 && red >= 0 && red <= 20) {
				 return true;
             }

			return false;
		}
		public static boolean isCalcaoHomer(CvScalar scalarExtraiRgb) {
			double blue = scalarExtraiRgb.val(0);
			double green = scalarExtraiRgb.val(1);
			double red = scalarExtraiRgb.val(2);
			
			if (blue >= 150 && blue <= 180 && green >= 98 && green <= 120 && red >= 0 && red <= 90) {
				return true;
			}
			
			return false;
		}
		public static boolean isBocaHomer(CvScalar scalarExtraiRgb) {
			double blue = scalarExtraiRgb.val(0);
			double green = scalarExtraiRgb.val(1);
			double red = scalarExtraiRgb.val(2);
			
			if (blue >= 95 && blue <= 140 && green >= 160 && green <= 185 && red >= 175 && red <= 200) {
				return true;
			}
			
			return false;
		}
		public static boolean isSapatoHomer(CvScalar scalarExtraiRgb) {
			double blue = scalarExtraiRgb.val(0);
			double green = scalarExtraiRgb.val(1);
			double red = scalarExtraiRgb.val(2);
			
			if (blue >= 25 && blue <= 45 && green >= 25 && green <= 45 && red >= 25 && red <= 45) {
				return true;
			}
			
			return false;
		}
		
		public float getLaranjaCamisaBart() {
			return laranjaCamisaBart;
		}
		public float getAzulCalcaoBart() {
			return azulCalcaoBart;
		}
		public float getAzulSapatoBart() {
			return azulSapatoBart;
		}
		public float getAzulCalcaHomer() {
			return azulCalcaHomer;
		}
		public float getMarromBocaHomer() {
			return marromBocaHomer;
		}
		public float getCinzaSapatoHomer() {
			return cinzaSapatoHomer;
		}
		
		public Builder withLaranjaCamisaBart(float laranjaCamisaBart) {
			this.laranjaCamisaBart = laranjaCamisaBart;
			return this;
		}
		public Builder withAzulCalcaoBart(float azulCalcaoBart) {
			this.azulCalcaoBart = azulCalcaoBart;
			return this;
		}
		public Builder withAzulSapatoBart(float azulSapatoBart) {
			this.azulSapatoBart = azulSapatoBart;
			return this;
		}
		public Builder withAzulCalcaHomer(float azulCalcaHomer) {
			this.azulCalcaHomer = azulCalcaHomer;
			return this;
		}
		public Builder withMarromBocaHomer(float marromBocaHomer) {
			this.marromBocaHomer = marromBocaHomer;
			return this;
		}
		public Builder withCinzaSapatoHomer(float cinzaSapatoHomer) {
			this.cinzaSapatoHomer = cinzaSapatoHomer;
			return this;
		}
		public Builder withClassePersonagem(int classePersonagem) {
			this.classePersonagem = classePersonagem;
			return this;
		}
		public Builder withClassePersonagemString(String classePersonagemString) {
			this.classePersonagemString = classePersonagemString;
			return this;
		}
		public Builder withImagemOriginalHeight(int imagemOriginalHeight) {
			this.imagemOriginalHeight = imagemOriginalHeight;
			return this;
		}
		public Builder withImagemOriginalWidth(int imagemOriginalWidth) {
			this.imagemOriginalWidth = imagemOriginalWidth;
			return this;
		}
		
		public Builder withLaranjaCamisaBartSoma1() {
			this.laranjaCamisaBart++;
			return this;
		}
		public Builder withAzulCalcaoBartSoma1() {
			this.azulCalcaoBart++;
			return this;
		}
		public Builder withSaparoBartSoma1() {
			this.azulSapatoBart++;
			return this;
		}
		public Builder withCalcaoHomerSoma1() {
			this.azulCalcaHomer++;
			return this;
		}
		public Builder withBocaHomerSoma1() {
			this.marromBocaHomer++;
			return this;
		}
		public Builder withSapatoHomerSoma1() {
			this.cinzaSapatoHomer++;
			return this;
		}
		
		public CaracteristicasHomerAndBart buildCaracteristicas() {
			return new CaracteristicasHomerAndBart(this);
		}
		
		public int getClassePersonagem() {
			return classePersonagem;
		}
		public String getClassePersonagemString() {
			return classePersonagemString;
		}

	}
}
