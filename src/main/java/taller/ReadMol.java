package taller;

import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainerSet;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.silent.AtomContainerSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.io.iterator.DefaultIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;

import org.openscience.cdk.smiles.SmilesParser;

public class ReadMol {

    public static IAtomContainerSet readCsvWithSmiles(String path) throws FileNotFoundException, InvalidSmilesException {

        Scanner sc = new Scanner(new File(path));
        sc.nextLine();
        IAtomContainerSet set = new AtomContainerSet();
        while (sc.hasNext()){
            String smile = sc.nextLine().split(",")[1];
            SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
            set.addAtomContainer(smilesParser.parseSmiles(smile));
        }
        return set;
    }

    public static IAtomContainerSet readSdf(String path) throws FileNotFoundException {
        IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
        FileInputStream sdfStream = new FileInputStream(path);
        DefaultIteratingChemObjectReader<IAtomContainer> reader = new IteratingSDFReader(sdfStream, builder);

        IAtomContainerSet set = new AtomContainerSet();
        while (reader.hasNext()) {
                IAtomContainer molecule = reader.next();
                set.addAtomContainer(molecule);
            }
        return set;
    }
}
