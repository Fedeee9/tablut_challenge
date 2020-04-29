package it.unibo.ai.didattica.competition.tablut.teampedro.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class TeamPedroBlackTablutClient {
	
		public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
			String[] array = new String[]{"BLACK"};
			if (args.length>0){
				array = new String[]{"BLACK", args[0]};
			}
			TeamPedroTablutClient.main(array);
		}
}

