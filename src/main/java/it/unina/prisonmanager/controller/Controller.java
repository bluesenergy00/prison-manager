package it.unina.prisonmanager.controller;

import java.util.Objects;

import it.unina.prisonmanager.dao.DataAccessObject;
import it.unina.prisonmanager.view.ClosableView;

public abstract class Controller
{
	protected final ClosableView closableView;
	protected final DataAccessObject<?, ?> dataAccessObject;
	
	public Controller(
		DataAccessObject<?, ?> dataAccessObject,
		ClosableView closableView
	) {
		this.closableView = Objects.requireNonNull(
			closableView, "ClosableView reference is NULL."
		);
		this.dataAccessObject = Objects.requireNonNull(
			dataAccessObject, "DataAccessObject reference is NULL."
		);
	}
}
