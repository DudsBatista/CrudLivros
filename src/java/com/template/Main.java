package com.template;

import com.template.controller.MainController;
import com.template.service.ILivroService;
import com.template.service.LivroService;
import com.template.validator.ILivroValidator;
import com.template.validator.LivroValidator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Classe principal da aplicação.
 *
 * É aqui que as implementações concretas são criadas
 * e injetadas no Controller.
 *
 * Isso aplica o princípio da Inversão da Dependência (DIP).
 */
public class Main extends Application {

    @Override
    public void start(Stage stage)
            throws Exception {

        /*
         * Cria as implementações concretas.
         */
        ILivroService livroService =
                new LivroService();

        ILivroValidator livroValidator =
                new LivroValidator();

        /*
         * Cria o FXMLLoader.
         */
        FXMLLoader loader =
                new FXMLLoader(
                        Main.class.getResource(
                                "main.fxml"
                        )
                );

        /*
         * Factory responsável por criar o Controller.
         *
         * Dessa forma, conseguimos passar as
         * dependências pelo construtor.
         */
        loader.setControllerFactory(
                classe -> {

                    if (classe
                            == MainController.class) {

                        return new MainController(
                                livroService,
                                livroValidator
                        );
                    }

                    try {

                        return classe
                                .getDeclaredConstructor()
                                .newInstance();

                    } catch (Exception e) {

                        throw new RuntimeException(e);
                    }
                }
        );

        /*
         * Carrega a tela.
         */
        Scene scene =
                new Scene(
                        loader.load(),
                        600,
                        400
                );

        stage.setTitle(
                "Cadastro de Livros"
        );

        stage.setScene(scene);

        stage.show();
    }

    public static void main(
            String[] args
    ) {

        launch();
    }
}