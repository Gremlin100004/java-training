package com.senla.carservice.api;

import com.senla.carservice.api.action.*;

public final class Builder {
    private static Builder instance;
    private Menu rootMenu;

    public Builder() {
    }

    public static Builder getInstance() {
        if (instance == null) {
            instance = new Builder();
        }
        return instance;
    }

    public void buildMenu(){
        this.rootMenu = new Menu("Car Service Menu");
        Menu mastersMenu = new Menu("Masters");
        Menu ordersMenu = new Menu("Orders");
        Menu garagesMenu = new Menu("Garages");
        this.rootMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Masters", MoveMasterActionImpl.getInstance()),
                new MenuItem("Orders", MoveOrdersActionImpl.getInstance()),
                new MenuItem("Garages", MoveGaragesActionImpl.getInstance())});
        mastersMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Show list of masters", ShowMastersActionImpl.getInstance()),
                new MenuItem("Add master", AddMasterActionImpl.getInstance()),
                new MenuItem("Delete Master", DeleteMasterActionImpl.getInstance()),
                new MenuItem("Show a list of masters sorted alphabetically", AlphabetListMasterActionImpl.getInstance()),
                new MenuItem("Show list of masters sorted by busy", BusyListMastersActionImpl.getInstance())
        });
        ordersMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Show list of masters", ShowMastersActionImpl.getInstance()),
                new MenuItem("Add master", AddMasterActionImpl.getInstance()),
                new MenuItem("Delete Master", DeleteMasterActionImpl.getInstance()),
                new MenuItem("Show a list of masters sorted alphabetically", AlphabetListMasterActionImpl.getInstance()),
                new MenuItem("Show list of masters sorted by busy", BusyListMastersActionImpl.getInstance())
        });
        garagesMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Show list of garages", ShowMastersActionImpl.getInstance()),
                new MenuItem("Add garage", AddMasterActionImpl.getInstance()),
                new MenuItem("Delete garage", DeleteMasterActionImpl.getInstance()),
                new MenuItem("Add place in garage", AlphabetListMasterActionImpl.getInstance()),
                new MenuItem("Delete place in garage", BusyListMastersActionImpl.getInstance())
        });



    }
    public Menu getRootMenu(){
        return this.rootMenu;
    }
}
