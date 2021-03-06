package it.unibo.ai.didattica.competition.tablut.teampedro.algorithms;

import java.util.List;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
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
import it.unibo.ai.didattica.competition.tablut.teampedro.heuristics.BlackHeuristicPedro;
import it.unibo.ai.didattica.competition.tablut.teampedro.heuristics.Heuristic;
import it.unibo.ai.didattica.competition.tablut.teampedro.heuristics.WhiteHeuristicPedro;
import it.unibo.ai.didattica.competition.tablut.teampedro.util.GameManager;
import it.unibo.ai.didattica.competition.tablut.teampedro.util.StatsManager;

public class MinMaxConcurrent extends Thread {

	private List<Node> nodes;
	private long endTime;
	private Heuristic heuristic;
	private int depthToReach;

	public MinMaxConcurrent(List<Node> nodes, int d, long endTime) {

		this.nodes = nodes;

		if (GameManager.getInstance().getPlayer().equalsIgnoreCase("black")) {
			this.heuristic = new BlackHeuristicPedro();
		} else if (GameManager.getInstance().getPlayer().equalsIgnoreCase("white")) {
			this.heuristic = new WhiteHeuristicPedro();
		}
		this.endTime = endTime;
		this.depthToReach = d;
	}

	@Override
	public void run() {

		for (Node n : this.nodes) {

			try {
				n.setValue(minValue(n, this.depthToReach, this.depthToReach, Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY));
			} catch (BoardException | ActionException | StopException | PawnException | DiagonalException
					| ClimbingException | ThroneException | OccupitedException | ClimbingCitadelException
					| CitadelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private double maxValue(Node node, int depth, int maxDepth, double alpha, double beta)
			throws BoardException, ActionException, StopException, PawnException, DiagonalException, ClimbingException,
			ThroneException, OccupitedException, ClimbingCitadelException, CitadelException {

		if (System.currentTimeMillis() > this.endTime) {

			return 0;
		}

		if (depth == 0 || node.getState().getTurn().equalsTurn("WW") || node.getState().getTurn().equalsTurn("BW")) {
			return this.heuristic.heuristic(node.getState());

		}

		List<Action> possibleMoves = GameManager.getInstance().getRules().getNextMovesFromState(node.getState());

		Double v = Double.NEGATIVE_INFINITY;

		for (Action a : possibleMoves) {

			it.unibo.ai.didattica.competition.tablut.domain.State nextState = GameManager.getInstance().getRules()
					.movePawn(node.getState().clone(), a);


			Node n = new Node(nextState, Double.POSITIVE_INFINITY, a);

			StatsManager.getInstance().incrementExpandedNodes();

			v = Math.max(v, minValue(n, depth - 1, maxDepth, alpha, beta));

			n.setValue(v);

			if (v >= beta)
				return v;

			alpha = Math.max(alpha, v);

			if (System.currentTimeMillis() > this.endTime) {

				return 0;
			}
		}

		return v;
	}

	private double minValue(Node node, int depth, int maxDepth, double alpha, double beta)
			throws BoardException, ActionException, StopException, PawnException, DiagonalException, ClimbingException,
			ThroneException, OccupitedException, ClimbingCitadelException, CitadelException {

		if (System.currentTimeMillis() > this.endTime) {

			return 0;
		}
		if (depth == 0 || node.getState().getTurn().equalsTurn("WW") || node.getState().getTurn().equalsTurn("BW")) {
			return this.heuristic.heuristic(node.getState());
		}

		List<Action> possibleMoves = GameManager.getInstance().getRules().getNextMovesFromState(node.getState());

		Double v = Double.POSITIVE_INFINITY;
		for (Action a : possibleMoves) {
			it.unibo.ai.didattica.competition.tablut.domain.State nextState = GameManager.getInstance().getRules()
					.movePawn(node.getState().clone(), a);

			Node n = new Node(nextState, Double.NEGATIVE_INFINITY, a);

			StatsManager.getInstance().incrementExpandedNodes();

			v = Math.min(v, maxValue(n, depth - 1, maxDepth, alpha, beta));

			n.setValue(v);

			if (v <= alpha)

				return v;

			beta = Math.min(beta, v);

			if (System.currentTimeMillis() > this.endTime) {

				return 0;
			}

		}
		possibleMoves.clear();

		return v;
	}
}
