package com.senla.carservice.api;

import java.util.Scanner;

public class MenuController {
    Builder builder;
    Navigator navigator;

    public MenuController() {
        this.builder = Builder.getInstance();
        this.builder.buildMenu();
        this.navigator = Navigator.getInstance(this.builder.getRootMenu());
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            this.navigator.printMenu();
            System.out.println("Enter number item menu:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int answer = scanner.nextInt();
            if (answer == 0){
                break;
            }
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


