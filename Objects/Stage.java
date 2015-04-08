package Objects;

import java.awt.geom.Point2D;

import Agenda.AgendaStage;

public class Stage extends DrawObject
{
	AgendaStage stage;
	
	public Stage(Point2D position, AgendaStage st)
	{
		super("stage", position);
		stage = st;
	}

	@Override
	public String getName()
	{
		return "Stage";
	}

	public AgendaStage getStage()
	{
		return stage;
	}

	public void setStage(AgendaStage stage)
	{
		this.stage = stage;
	}

}
