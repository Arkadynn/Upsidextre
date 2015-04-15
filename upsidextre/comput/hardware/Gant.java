package upsidextre.comput.hardware;

public class Gant {
	private Doigt indexe;
	private Doigt majeur;
	private Doigt pouce;
	
	public Gant() {
		this.setIndexe(new Indexe());
		this.setMajeur(new Majeur());
		this.setPouce(new Pouce());
	}

	public Doigt getIndexe() {
		return indexe;
	}

	public void setIndexe(Doigt indexe) {
		this.indexe = indexe;
	}

	public Doigt getMajeur() {
		return majeur;
	}

	public void setMajeur(Doigt majeur) {
		this.majeur = majeur;
	}

	public Doigt getPouce() {
		return pouce;
	}

	public void setPouce(Doigt pouce) {
		this.pouce = pouce;
	}
}
