package com.alura.literalura.client;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
