package util;

import information.Produto;
import information.Rfid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ProdutosRfidMDL;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amanda on 04/12/2016.
 */
public class RfidImportacao {

    private ObservableList<ProdutosRfidMDL> produtos = FXCollections.observableArrayList();

    private String separador = "\\|";

    public ObservableList<ProdutosRfidMDL> getProdutos() {
        return produtos;
    }

    public void setProdutos(ObservableList<ProdutosRfidMDL> produtos) {
        this.produtos = produtos;
    }

    public String getSeparador() {
        return separador;
    }

    public void setSeparador(String separador) {
        this.separador = separador;
    }

    public int importar(Rfid rfid) throws Exception {
        if (rfid == null)
            throw new Exception("Rfid nulo!");

        if (rfid.getPath().trim() == "")
            throw new Exception("Caminho Rfid em branco!");

        File file = new File(rfid.getPath());

        if (!file.exists())
            throw new Exception("Arquivo Rfid '" + rfid.getPath() + "' não existe!");


        List<String> ret = Files.readAllLines(file.toPath());

        String[] vetProdutos = null;
        ProdutosRfidMDL prodMDL;
        Produto prodINF;

        String codigo = "";
        String descricao;
        String cadastrado;

        for (int i = 0; i < ret.size(); i++) {
            vetProdutos = ret.get(i).split(getSeparador());

            for (int j = 0; j < vetProdutos.length; j++) {
                descricao = "";
                cadastrado = "Não";
                codigo = vetProdutos[j];

                prodINF = new Produto().get(codigo);

                if (prodINF.getDescricao() != null) {
                    if (prodINF.getDescricao().trim() != "")
                        descricao = prodINF.getDescricao();
                        cadastrado = "Sim";
                }

                prodMDL = new ProdutosRfidMDL(codigo, descricao, cadastrado);
                produtos.add(prodMDL);
            }
        }

        return produtos.size();
    }
}
