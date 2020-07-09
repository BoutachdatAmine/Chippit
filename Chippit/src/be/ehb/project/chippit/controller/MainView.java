/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package be.ehb.project.chippit.controller;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Angie
 */
public class MainView extends Application {

    //Login
    public static String login = "login";
    public static String loginFile = "../view/login.fxml";
    public static String authentication = "authentication";
    public static String authenticationFile = "../view/twoAuth.fxml";
    //Modules
    public static String home = "home";
    public static String homeFile = "../view/home.fxml";
    public static String customer = "customer";
    public static String customerFile = "../view/customersModule.fxml";
    public static String warehouse = "warehouse";
    public static String warehouseFile = "../view/warehouseModule.fxml";
    public static String finance = "finance";
    public static String financeFile = "../view/financeModule.fxml";
    public static String shop = "shop";
    public static String shopFile = "../view/shopModule.fxml";
    public static String marketing = "marketing";
    public static String marketingFile = "../view/marketingModule.fxml";
    //Add
    public static String addUser = "addUser";
    public static String addUserFile = "../view/addUser.fxml";
    public static String addProduct = "addProduct";
    public static String addProductFile = "../view/addProduct.fxml";
    public static String addCategory = "addCategory";
    public static String addCategoryFile = "../view/addCategory.fxml";
    //Update
    public static String updateUser = "updateUser";
    public static String updateUserFile = "../view/updateUser.fxml";
    public static String updateCategory = "updateCategory";
    public static String updateCategoryFile = "../view/updateCategory.fxml";
    public static String updateProduct = "updateProduct";
    public static String updateProductFile = "../view/updateProduct.fxml";


    @Override
    public void start(Stage stage) {

        ScreensController mainContainer = new ScreensController();

        mainContainer.loadScreen(MainView.login, MainView.loginFile);
        mainContainer.loadScreen(MainView.authentication, MainView.authenticationFile);
        mainContainer.loadScreen(MainView.home, MainView.homeFile);
        mainContainer.loadScreen(MainView.customer, MainView.customerFile);
        mainContainer.loadScreen(MainView.warehouse, MainView.warehouseFile);
        mainContainer.loadScreen(MainView.finance, MainView.financeFile);
        mainContainer.loadScreen(MainView.shop, MainView.shopFile);
        mainContainer.loadScreen(MainView.marketing, MainView.marketingFile);
        mainContainer.loadScreen(MainView.addUser, MainView.addUserFile);
        mainContainer.loadScreen(MainView.addProduct, MainView.addProductFile);
        mainContainer.loadScreen(MainView.addCategory, MainView.addCategoryFile);
        mainContainer.loadScreen(MainView.updateUser, MainView.updateUserFile);
        mainContainer.loadScreen(MainView.updateCategory, MainView.updateCategoryFile);
        mainContainer.loadScreen(MainView.updateProduct, MainView.updateProductFile);


        //Set loginscreen as first screen
        mainContainer.setScreen(MainView.login);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, 1316, 627);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
