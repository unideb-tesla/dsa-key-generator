package hu.unideb.inf.tesla;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Scanner;

public class Main {

	public static final String PROVIDER_SUN = "SUN";

	public static final String DEFAULT_DIGITAL_SIGNATURE_ALGORITHM = "DSA";
	public static final int DEFAULT_KEY_SIZE = 1024;

	public static final String DEFAULT_OUTPUT_FOLDER_NAME = "output";
	public static final String PUBLIC_KEY_FILE_NAME = "public.key";
	public static final String PRIVATE_KEY_FILE_NAME = "private.key";

	public static final int ANSWER_NO = 0;
	public static final int ANSWER_YES = 1;

	public static void main(String[] args) {

		System.out.println("=== DSA KEY GENERATOR ===\n");

		// scanner object potentially used in later
		Scanner scanner = new Scanner(System.in);

		// check if the output folder is provided as a command line argument
		String outputFolderName = DEFAULT_OUTPUT_FOLDER_NAME;

		if (args.length == 1) {
			outputFolderName = args[0];
		} else if (args.length > 1) {
			System.err.println("Only 1 command line argument (the name of the output folder) is provided!");
			return;
		}

		// check if output folder exists
		File outputFolder = new File(outputFolderName);

		if (!outputFolder.exists() || !outputFolder.isDirectory()) {
			System.out.println("Creating folder '" + outputFolderName + "'...");
			outputFolder.mkdir();
			System.out.println("Folder has been created successfully!");
			System.out.println();
		}

		// generate keypair
		System.out.println("Keypair settings:");
		System.out.println("Provider: " + PROVIDER_SUN);
		System.out.println("Algorithm: " + DEFAULT_DIGITAL_SIGNATURE_ALGORITHM);
		System.out.println("Key size: " + DEFAULT_KEY_SIZE + " bit");
		System.out.println();

		System.out.println("Generating keypair...");

		KeyPair keyPair = null;
		try {

			keyPair = generateKeyPair(DEFAULT_DIGITAL_SIGNATURE_ALGORITHM, DEFAULT_KEY_SIZE);
			System.out.println("Keypair generated successfully!");

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			System.err.println("Couldn't generate keypair.");
			return;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.err.println("Couldn't generate keypair.");
			return;
		}

		System.out.println();

		// check location
		File publicKeyFile = new File(outputFolder, PUBLIC_KEY_FILE_NAME);
		File privateKeyFile = new File(outputFolder, PRIVATE_KEY_FILE_NAME);

		if (publicKeyFile.exists() || privateKeyFile.exists()) {

			System.out.print("It seems that previously generated key files exist in the given folder. Would you like to override them? (Y/N) ");

			int answer = readYesOrNoAnswer(scanner);

			if (answer == ANSWER_NO) {
				return;
			}

			System.out.println();

		}

		// save files
		System.out.println("Saving public key...");
		try {

			saveKeyToFile(keyPair.getPublic().getEncoded(), publicKeyFile.getAbsolutePath());
			System.out.println("Public key saved successfully!");
			System.out.println();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't save public key.");
			return;
		}

		System.out.println("Saving private key...");
		try {

			saveKeyToFile(keyPair.getPrivate().getEncoded(), privateKeyFile.getAbsolutePath());
			System.out.println("Private key saved successfully!");
			System.out.println();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't save private key.");
			return;
		}

		// end of program
		scanner.close();

		System.out.println("=== END OF PROGRAM ===");

	}

	public static KeyPair generateKeyPair(String digitalSignatureAlgorithm, int keySize) throws NoSuchProviderException, NoSuchAlgorithmException {

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(digitalSignatureAlgorithm, PROVIDER_SUN);

		SecureRandom random = new SecureRandom();

		keyGen.initialize(keySize, random);

		KeyPair pair = keyGen.generateKeyPair();

		return pair;

	}

	public static int readYesOrNoAnswer(Scanner scanner) {

		int answer = -1;
		String input;

		while (answer < 0) {

			input = scanner.nextLine().trim().toLowerCase();

			if (input.equals("y") || input.equals("yes") || input.equals("ye")) {
				answer = ANSWER_YES;
			} else if (input.equals("n") || input.equals("no") || input.equals("nah")) {
				answer = ANSWER_NO;
			}

		}

		return answer;

	}

	public static void saveKeyToFile(byte[] key, String fileName) throws IOException {

		File outputFile = new File(fileName);

		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

		fileOutputStream.write(key);

		fileOutputStream.close();

	}


}
