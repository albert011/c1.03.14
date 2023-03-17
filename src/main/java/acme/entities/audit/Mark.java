
package acme.entities.audit;

public enum Mark {

	APlus("A+"), A("A"), B("B"), C("C"), F("F"), FMinus("F-");


	public final String value;


	private Mark(final String value) {
		this.value = value;
	}
}
