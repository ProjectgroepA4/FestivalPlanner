package Objects;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Agenda.Agenda;
import Agenda.AgendaStage;

public class Stage extends DrawObject {
	AgendaStage stage;

	public Stage(Point2D position, AgendaStage stage) {
		super("stage", position);
		this.stage = stage;
	}

	@Override
	public String getName() {
		return "Stage";
	}

	public AgendaStage getStage() {
		return stage;
	}

}
