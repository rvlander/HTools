/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expdo.experience;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author rvlander
 */
public class ExpIO {
    
    
    static String smanip= "manip";
    static String sname= "name";
    static String sexperience = "experience";
    static String siter= "iter";
    static String sauto= "auto";
    static String swidth="width";
    static String sheight="height";
    static String ssaveimage="saveimage";
    
    
    
    

    public static void main(String[] args) throws JDOMException, IOException {
        DefaultMutableTreeNode p = readXML("./ExpExemples/xp1.xml");
        writeXML("/home/rvlander/Desktop/toto.xml", p);
    }

    //Ajouter cette méthodes à la classe JDOM2
    public static DefaultMutableTreeNode readXML(String xmlfile) throws JDOMException, IOException {

        Document document;
        Element racine;
        SAXBuilder sxb = new SAXBuilder();
        DefaultMutableTreeNode root;

        //On crée un nouveau document JDOM avec en argument le fichier XML
        //Le parsing est terminé ;)
        document = sxb.build(new File(xmlfile));


        //On initialise un nouvel élément racine avec l'élément racine du document.
        racine = document.getRootElement();
        
        int iter = Integer.parseInt(racine.getAttributeValue(siter));
        boolean save_image = false;
        String im = racine.getAttributeValue(ssaveimage);
        if(im!=null && im.equals("yes")) save_image=true;
        
        Manip man = new Manip(racine.getAttributeValue(sname),iter,save_image);
        root = new DefaultMutableTreeNode(man);
        //On crée une List contenant tous les noeuds "etudiant" de l'Element racine
        List listEtudiants = racine.getChildren(sexperience);

        //On crée un Iterator sur notre liste
        Iterator i = listEtudiants.iterator();
        while (i.hasNext()) {
            //On recrée l'Element courant à chaque tour de boucle afin de
            //pouvoir utiliser les méthodes propres aux Element comme :
            //selectionner un noeud fils, modifier du texte, etc...
            Element courant = (Element) i.next();
            //On affiche le nom de l'element courant
            boolean auto=false;
            String res = courant.getAttributeValue(sauto);
            if(res!=null && res.equals("yes")) auto=true;
            Experience e = new Experience(courant.getText(),courant.getAttributeValue(sname),auto);
            res = courant.getAttributeValue(swidth);
            if(res!=null) e.setWidth(Integer.parseInt(res));
            res = courant.getAttributeValue(sheight);
            if(res!=null) e.setHeight(Integer.parseInt(res));
            
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(e);
            for(int j=1;j<=iter;j++){
                node.add(new DefaultMutableTreeNode(j));
            }
            root.add(node);
        }
        
        return root;
    }

    public static void writeXML(String xmlfile, DefaultMutableTreeNode root) throws JDOMException, IOException {

        Document document;
        Element racine;
        SAXBuilder sxb = new SAXBuilder();
        Manip man = (Manip) root.getUserObject();


        racine = new Element(smanip);
        racine.setAttribute(sname, man.getName());
        racine.setAttribute(siter, ""+man.getIter());
        racine.setAttribute(ssaveimage, ""+man.isSaveImage());
        for (Enumeration ee = root.children();ee.hasMoreElements();) {
            Experience p = (Experience) ((DefaultMutableTreeNode)ee.nextElement()).getUserObject();
            Element experience = new Element(sexperience);
            racine.addContent(experience);
            experience.setText(p.getConsigne());
            experience.setAttribute(sname, p.getName());
            experience.setAttribute(sauto, p.getAutoNext()?"yes":"no");
            experience.setAttribute(swidth, p.getWidth()+"");
            experience.setAttribute(sheight, p.getHeight()+"");
        }

        document = new Document(racine);

        //On utilise ici un affichage classique avec getPrettyFormat()
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
        //avec en argument le nom du fichier pour effectuer la sérialisation.
        sortie.output(document, new FileOutputStream(xmlfile));

    }
}
