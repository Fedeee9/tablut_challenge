package it.unibo.ai.didattica.competition.tablut.teampedro.client;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.UnknownHostException;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import it.unibo.ai.didattica.competition.tablut.exceptions.ActionException;
import it.unibo.ai.didattica.competition.tablut.exceptions.BoardException;
import it.unibo.ai.didattica.competition.tablut.exceptions.CitadelException;
import it.unibo.ai.didattica.competition.tablut.exceptions.ClimbingCitadelException;
import it.unibo.ai.didattica.competition.tablut.exceptions.ClimbingException;
import it.unibo.ai.didattica.competition.tablut.exceptions.DiagonalException;
import it.unibo.ai.didattica.competition.tablut.exceptions.OccupitedException;
import it.unibo.ai.didattica.competition.tablut.exceptions.PawnException;
import it.unibo.ai.didattica.competition.tablut.exceptions.StopException;
import it.unibo.ai.didattica.competition.tablut.exceptions.ThroneException;
import it.unibo.ai.didattica.competition.tablut.teampedro.algorithms.AlphaBetaConcurrent;
import it.unibo.ai.didattica.competition.tablut.teampedro.algorithms.AlphaBetaIterative;

import it.unibo.ai.didattica.competition.tablut.teampedro.algorithms.IA;
import it.unibo.ai.didattica.competition.tablut.teampedro.domain.MyGameAshtonTablutRules;
import it.unibo.ai.didattica.competition.tablut.teampedro.domain.MyGameModernTablutRules;
import it.unibo.ai.didattica.competition.tablut.teampedro.domain.MyGameTablutRules;
import it.unibo.ai.didattica.competition.tablut.teampedro.domain.MyRules;
import it.unibo.ai.didattica.competition.tablut.teampedro.gui.GuiCli;
import it.unibo.ai.didattica.competition.tablut.teampedro.util.GameManager;
import it.unibo.ai.didattica.competition.tablut.teampedro.util.StatsManager;

public class TeamPedroTablutClient extends TablutClient {

	private int game;
	private IA ia;
	private GuiCli gui;

	public TeamPedroTablutClient(String player, String name, int gameChosen, int timeout, String ipAddress)
			throws UnknownHostException, IOException {
		super(player, name, timeout, ipAddress);
		this.game = gameChosen;
		this.gui = new GuiCli();
		MyRules rules = null;

		switch (this.game) {
		case 1:
			rules = MyGameTablutRules.getInstance();
			break;
		case 2:
			rules = MyGameModernTablutRules.getInstance();
			break;
		case 3:
			rules = MyGameTablutRules.getInstance();
			break;
		case 4:

			rules = MyGameAshtonTablutRules.getInstance();
			break;
		default:
			System.out.println("Error in game selection");
			System.exit(4);
		}

		GameManager.getInstance().setRules(rules);

		//this.ia = new AlphaBetaIterative();
		this.ia = new AlphaBetaConcurrent();

	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

		int gametype = 4;
		String role = "";
		String name = "TEAMPEDRO";
		String ipaddress = "";
		int timeout = 55;
		int depth = 8;
		int numThread = 8;

		/*String usage = "Usage: java teampedro COLOR [-t <timeout>] [-p <core>] [-d <max_depth>]\n"
				+ "\t<timeout> must be an integer (default 55)\n" + "\t<core> must be an integer (default 8\n"
				+ "\t<max_depth> must be an integer (default 8)\n";*/
		
		String usageT = "Usage: java teampedro COLOR timeout(default 55) ipAddress\n";

		// TODO: change the behavior?
		if (args.length < 1) {
			System.out.println("You must specify which player you are (WHITE or BLACK)");
			System.exit(-1);
		} else {
			role = (args[0]);
			
			try {
				timeout = Integer.parseInt(args[1]);
				if (timeout < 1) {
					System.out.println("Timeout format not allowed!");
					System.exit(1);
				}
			} catch (Exception e) {
				System.out.println("Timeout format is not correct!");
				System.out.println(usageT);
				System.exit(1);
			}
			
			ipaddress = (args[2]);
		}

		/*for (int i = 1; i < args.length - 1; i++) {

			if (args[i].equals("-t")) {
				i++;
				try {
					timeout = Integer.parseInt(args[i]) - 2;
					if (timeout < 1) {
						System.out.println("Timeout format not allowed!");
						System.out.println(args[i]);
						System.exit(1);
					}
				} catch (Exception e) {
					System.out.println("Timeout format is not correct!");
					System.out.println(usage);
					System.exit(1);
				}
			}

			if (args[i].equals("-p")) {
				i++;
				try {
					numThread = Integer.parseInt(args[i]);
					if (numThread < 1) {
						System.out.println("Core format not allowed!");
						System.out.println(args[i]);
						System.exit(1);
					}
				} catch (Exception e) {
					System.out.println("Core format is not correct!");
					System.out.println(args[i]);
					System.out.println(usage);
					System.exit(1);
				}
			}

			if (args[i].equals("-d")) {
				i++;
				try {
					depth = Integer.parseInt(args[i]);
					if (depth < 1) {
						System.out.println("Depth format not allowed!");
						System.out.println(args[i]);
						System.out.println(usage);
						System.exit(1);
					}
				} catch (Exception e) {
					System.out.println("Depth format is not correct!");
					System.out.println(args[i]);
					System.out.println(usage);
					System.exit(1);
				}

			}
		}*/

		printName();

		System.out.println("Timeout: " + timeout);
		System.out.println("Core: " + numThread);
		System.out.println("Max depth: " + depth);

		GameManager.getInstance().setParameters(timeout, depth, role.toLowerCase());
		GameManager.getInstance().setNumThread(numThread);
		TeamPedroTablutClient client = new TeamPedroTablutClient(role, name, gametype, timeout, ipaddress);
		client.run();
	}

	private synchronized static void printName() {
		System.out.println();
		System.out.println("\r\n" + 
				" _______  _______  _______  __   __    _______  _______  ______   ______    _______ \r\n" + 
				"|       ||       ||   _   ||  |_|  |  |       ||       ||      | |    _ |  |       |\r\n" + 
				"|_     _||    ___||  |_|  ||       |  |    _  ||    ___||  _    ||   | ||  |   _   |\r\n" + 
				"  |   |  |   |___ |       ||       |  |   |_| ||   |___ | | |   ||   |_||_ |  | |  |\r\n" + 
				"  |   |  |    ___||       ||       |  |    ___||    ___|| |_|   ||    __  ||  |_|  |\r\n" + 
				"  |   |  |   |___ |   _   || ||_|| |  |   |    |   |___ |       ||   |  | ||       |\r\n" + 
				"  |___|  |_______||__| |__||_|   |_|  |___|    |_______||______| |___|  |_||_______|\r\n" + 
				"");
		System.out.println();
	}

	@Override
	public void run() {
		
		StatsManager.getInstance().setStartTotal(System.currentTimeMillis());

		try {
			this.declareName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		State state;

		switch (this.game) {
		case 1:
			state = new StateTablut();
			break;
		case 2:
			state = new StateTablut();
			break;
		case 3:
			state = new StateBrandub();
			break;
		case 4:
			state = new StateTablut();
			state.setTurn(State.Turn.WHITE);
			System.out.println("Ashton Tablut game");
			break;
		default:
			System.out.println("Error in game selection");
			System.exit(4);
		}

		System.out.println("You are player " + this.getPlayer().toString() + "!");

		while (true) {
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(1);
			}
			System.out.println("Current state:");
			state = this.getCurrentState();
			this.gui.update(state);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			GameManager.getInstance().addVisitedState(state);

			if (this.getPlayer().equals(Turn.WHITE)) {
				// è il mio turno quando sono il bianco
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
					Action a = null;
					try {
						a = new Action("z0", "z0", State.Turn.WHITE);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					try {

						a = this.ia.getBestAction(this.getCurrentState());

					} catch (BoardException | ActionException | StopException | PawnException | DiagonalException
							| ClimbingException | ThroneException | OccupitedException | ClimbingCitadelException
							| CitadelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					System.out.println("Chosen move: " + a.toString());
					try {
						this.write(a);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// è il turno dell'avversario
				else if (state.getTurn().equals(StateTablut.Turn.BLACK)) {
					//StatsManager.getInstance().printResults();
					System.out.println("Waiting for your opponent move... ");
				}
				// ho vinto
				else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					StatsManager.getInstance().setEndTotal(System.currentTimeMillis());
					System.out.println("YOU WIN!");
					StatsManager.getInstance().printTotalResults();
					System.exit(0);
				}
				// ho perso
				else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					StatsManager.getInstance().setEndTotal(System.currentTimeMillis());
					System.out.println("YOU LOSE!");
					StatsManager.getInstance().printTotalResults();
					System.exit(0);
				}
				// pareggio
				else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					StatsManager.getInstance().setEndTotal(System.currentTimeMillis());
					System.out.println("DRAW!");
					StatsManager.getInstance().printTotalResults();
					System.exit(0);
				}

			} else {

				// è il mio turno quando sono il nero
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
					Action a = null;

					try {
						a = new Action("z0", "z0", State.Turn.BLACK);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {

						a = this.ia.getBestAction(this.getCurrentState());

					} catch (BoardException | ActionException | StopException | PawnException | DiagonalException
							| ClimbingException | ThroneException | OccupitedException | ClimbingCitadelException
							| CitadelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					System.out.println("Chosen move: " + a.toString());
					try {
						this.write(a);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
					//StatsManager.getInstance().printResults();
					System.out.println("Waiting for your opponent move... ");
				} else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					StatsManager.getInstance().setEndTotal(System.currentTimeMillis());
					System.out.println("YOU LOSE!");
					StatsManager.getInstance().printTotalResults();
					System.exit(0);
				} else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					StatsManager.getInstance().setEndTotal(System.currentTimeMillis());
					System.out.println("YOU WIN!");
					StatsManager.getInstance().printTotalResults();
					System.exit(0);
				} else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					StatsManager.getInstance().setEndTotal(System.currentTimeMillis());
					System.out.println("DRAW!");
					StatsManager.getInstance().printTotalResults();
					System.exit(0);
				}
			}
		}
	}
}
