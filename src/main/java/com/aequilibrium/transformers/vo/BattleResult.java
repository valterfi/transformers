package com.aequilibrium.transformers.vo;

import com.aequilibrium.transformers.model.Transformer;

public class BattleResult {
	
	private Transformer winner;

	private Transformer loser;
	
	private boolean hasWinner = false;
	
	private boolean destroyAll = false;

	public static BattleResult builder() {
		return new BattleResult();
	}

	public BattleResult() {
	}

	public BattleResult withWinner(Transformer winner) {
		if (winner != null) {
			setHasWinner(true);
		}
		this.setWinner(winner);
		return this;
	}
	
	public BattleResult withLoser(Transformer loser) {
		this.setLoser(loser);
		return this;
	}
	
	public BattleResult shouldDestroyAll() {
		this.setDestroyAll(true);
		return this;
	}

	public Transformer getWinner() {
		return winner;
	}

	public void setWinner(Transformer winner) {
		if (winner != null) {
			setHasWinner(true);
		}
		this.winner = winner;
	}

	public Transformer getLoser() {
		return loser;
	}

	public void setLoser(Transformer loser) {
		this.loser = loser;
	}

	public boolean isDestroyAll() {
		return destroyAll;
	}

	public void setDestroyAll(boolean destroyAll) {
		this.destroyAll = destroyAll;
	}

	public boolean hasWinner() {
		return hasWinner;
	}

	public void setHasWinner(boolean hasWinner) {
		this.hasWinner = hasWinner;
	}

}
