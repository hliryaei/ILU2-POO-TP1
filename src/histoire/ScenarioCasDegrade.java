package histoire;
import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarioCasDegrade {
	public static void main(String[] args) { 
		Etal etal = new Etal(); 
		try {
			etal.libererEtal(); 
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			
		}
		
		Gaulois asterix = new Gaulois("Asterix", 10);
		etal.occuperEtal(asterix, "x", 10);
		try {
			etal.acheterProduit(0, null);
		}
		catch(NullPointerException | IllegalArgumentException e){
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");
	}

}
