package com.senla.carservice.api;

import com.senla.carservice.api.action.*;

import java.util.Scanner;

public class MenuController {
    Builder builder;
    Navigator navigator;

    public MenuController() {
        this.builder = Builder.getInstance();
    }

    public void run() {
        this.builder.buildMenu();
        Menu carOfficeMenu = this.builder.getRootMenu();
        carOfficeMenu.setName("Car Service Menu");
        Menu mastersMenu = this.builder.getRootMenu();
        mastersMenu.setName("Car Service Menu");
        Menu ordersMenu = this.builder.getRootMenu();
        ordersMenu.setName("Car Service Menu");
        Menu garagesMenu = this.builder.getRootMenu();
        garagesMenu.setName("Car Service Menu");
        carOfficeMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Masters", MoveMasterActionImpl.getInstance()),
                new MenuItem("Orders", MoveOrdersActionImpl.getInstance()),
                new MenuItem("Garages", MoveGaragesActionImpl.getInstance())});
        this.navigator = Navigator.getInstance(carOfficeMenu);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            this.navigator.printMenu();
            System.out.println("Enter number item menu:");
            int answer = scanner.nextInt();
            navigator.navigate(answer);
        }
    }
}






















//        MenuItem mastersItem = new MenuItem("Masters", GoMaster.getInstance(), mastersMenu);
//        MenuItem ordersItem = new MenuItem("Orders", GoOrders.getInstance(), ordersMenu);
//        MenuItem garagesItem = new MenuItem("Garages", GoGarages.getInstance(), garagesMenu);
//
//        MenuItem addMasterItem = new MenuItem("Add master to service", AddMasterActionImpl.getInstance(), carOfficeMenu);
//        MenuItem alphabetMastersItem = new MenuItem("Get masters sort by alphabet", AlphabetListMasterActionImpl.getInstance(), carOfficeMenu);
//        MenuItem busyMastersItem = new MenuItem("Get masters sort by busy", BusyListMastersActionImpl.getInstance(), carOfficeMenu);
//        MenuItem deleteMasterItem = new MenuItem("Delete master from service", DeleteMasterActionImpl.getInstance(), carOfficeMenu);
//
//        MenuItem[] officeMenuItems = new MenuItem[3];
//        officeMenuItems[0] = mastersItem;
//        officeMenuItems[1] = ordersItem;
//        officeMenuItems[2] = garagesItem;
//        carOfficeMenu.setMenuItems(officeMenuItems);
//
////        Start---------------------------------------------------------
//
//        this.builder.buildMenu();
//        carOfficeMenu = this.builder.getRootMenu();
//        carOfficeMenu.setName("Car Service Menu");
//        carOfficeMenu.setMenuItems(officeMenuItems);
//
//


