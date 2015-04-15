package upsidextre.comput.hardware;

public class Gant {
	private Indexe indexe;
	private Majeur majeur;
	private Pouce pouce;
	
	private Positionnement position;
	
	public Gant() {
		this.setIndexe(new Indexe());
		this.setMajeur(new Majeur());
		this.setPouce(new Pouce());
	}

	public Indexe getIndexe() {
		return indexe;
	}

	public void setIndexe(Indexe indexe) {
		this.indexe = indexe;
	}

	public Majeur getMajeur() {
		return majeur;
	}

	public void setMajeur(Majeur majeur) {
		this.majeur = majeur;
	}

	public Pouce getPouce() {
		return pouce;
	}

	public void setPouce(Pouce pouce) {
		this.pouce = pouce;
	}

	public Positionnement getPosition() {
		return position;
	}

	public void setPosition(Positionnement position) {
		this.position = position;
	}
}
