package com.senla.carservice.ui.action;

import com.senla.carservice.controller.OrderController;
import com.senla.carservice.ui.util.ScannerUtil;

public class ExportOrderActionImpl implements Action {
    public ExportOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = OrderController.getInstance();
        if (isContinue()) {
            System.out.println(orderController.exportOrders());
        }
    }

    private boolean isContinue() {
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            answer = ScannerUtil.getStringUser(
                    "For proper import, do not forget to export \"masters\" and \" " +
                            "garages\"!\n Would you like to continue export? y/n");
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("You have entered wrong answer!");
            }
        }
        return answer.equals("y");
    }
}