package upsidextre.comput.hardware;

public class Gant {
	private Doigt indexe;
	private Doigt majeur;
	private Doigt pouce;
	
	public Gant() {
		this.indexe = new Indexe();
		this.majeur = new Majeur();
		this.pouce = new Pouce();
	}
}
