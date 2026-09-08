package com.template.service;

import com.template.model.dto.LivroDTO;
import java.util.ArrayList;

public interface ILivroService {

    ArrayList<LivroDTO> listarLivros();

    void cadastrarLivro(
            String titulo,
            String autor,
            String anoTexto
    );

    LivroDTO buscarPorId(String idTexto);

    void atualizarLivro(
            String idTexto,
            String titulo,
            String autor,
            String anoTexto
    );

    void deletarLivro(String idTexto);
}