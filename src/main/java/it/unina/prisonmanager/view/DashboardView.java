package it.unina.prisonmanager.view;

public interface DashboardView extends ClosableView, MessageView
{
	void showHomePage();
	void showAssignedBlocks();
	void showCurrentStaffMembersView();
	void showStaffMemberView();
	void showOccupiedCells();
	void showCurrentInmatesView();
	void showInmateView();
	void showBlockView();
	void showCellView();
	void showUserView();
}
