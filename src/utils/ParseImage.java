package utils;

import java.awt.Image;

/**
 * La classe ParseImage !
 * Cette classe permet de representer un objet qui contient une image et son id.
 * 
 * @author  diallo-djiguine-diallo-diallo
 * @version 1.0
 * @since   2022-04-03
 */
public class ParseImage {
	private Image image;
	private String id;
	
	/**
	 * C'est le constructeur.
	 * @param id l'identifiant de l'image, le type String.
	 * @param image de type Image.
	 */
	public ParseImage(String id, Image image) {
		this.id = id;
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ParseImage [id=" + id + ", image=" + image + "]";
	}
	
}
