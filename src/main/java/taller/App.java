package taller;

import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import javax.swing.*;
import java.awt.Color;
import java.awt.image.BufferedImage;


public class App 
{
    public static void drawMolecule(IAtomContainer molecule) throws Exception {
        DepictionGenerator dptgen = new DepictionGenerator();
        dptgen.withSize(200, 250)
                .withMolTitle()
                .withTitleColor(Color.DARK_GRAY);
        BufferedImage im = dptgen.depict(molecule).toImg();

        // Crear un diálogo y mostrar la imagen
        JDialog dialog = new JDialog();
        dialog.setTitle("Molecule Drawing");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel(new ImageIcon(im));
        dialog.getContentPane().add(label);

        dialog.pack();
        dialog.setVisible(true);
    }

    public static void addH(IAtomContainer mol) throws CDKException {

        CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(SilentChemObjectBuilder.getInstance());
        for (IAtom atom : mol.atoms()) {
            IAtomType type = matcher.findMatchingAtomType(mol, atom);
            AtomTypeManipulator.configure(atom, type);
        }
        CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(mol.getBuilder());
        adder.addImplicitHydrogens(mol);
    }
    public static void printInfo(IAtomContainerSet mols) throws Exception {
        System.out.println("There are "+mols.getAtomContainerCount()+" molecules");

        for(int i = 0 ; i < mols.getAtomContainerCount() ; i++){
            IAtomContainer mol = mols.getAtomContainer(i);
            System.out.println("Molecule "+i+" has "+mol.getAtomCount()+" atoms");
        }

        IAtomContainer mol = mols.getAtomContainer(0);

        addH(mol);


        for(int i = 0 ; i < mol.getAtomCount() ; i++){
            String isInRing = mol.getAtom(i).isInRing() ? "Is in Ring":"Is not in Ring";
            String isAromatic = mol.getAtom(i).isAromatic() ? "Is an aromatic atom":"Is not an aromatic atom";
            System.out.println("Atom "+i+" has symbol "+mol.getAtom(i).getSymbol()+","
                    + " valence "+ mol.getAtom(i).getValency()+","
                    + isInRing +", "
                    + isAromatic);
        }
        drawMolecule(mol);

    }
    public static void main( String[] args ) throws Exception {
        String csvFile = "DILI-Liew_2_B_TS_47.csv";
        String sdfFile = "2_B_TS_47_act.sdf";

        IAtomContainerSet mols = ReadMol.readCsvWithSmiles(csvFile);

        printInfo(mols);

//        IAtomContainerSet molsSdf = ReadMol.readSdf(sdfFile);
//
//        printInfo(molsSdf);

    }
}
