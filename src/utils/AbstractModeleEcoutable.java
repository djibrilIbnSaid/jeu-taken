package utils;

import java.util.ArrayList;

public abstract class AbstractModeleEcoutable  implements ModeleEcoutable{
	
	private ArrayList<EcouteurModele> listEcouteur=new ArrayList<>();
	
	@Override
	public void ajoutEcouteur(EcouteurModele e) {
		listEcouteur.add(e);
	}
	
	@Override
	public void retraitEcouteur(EcouteurModele e) {
		listEcouteur.remove(e);
	}
	
	protected void fireChangement() { 
			for(EcouteurModele liste: listEcouteur) {
				liste.modeleMisAjour(this);
			}
	}
	
	
	
	
}
