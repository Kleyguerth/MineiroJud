package com.dgscofield.mavenproject1;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

/**
 *
 * @author kleyguerth
 */
@SuppressWarnings("serial")
public class Processo implements Serializable {
    private String processo = "";
    private String relator = "";
    private String relatorParaAcordao = "";
    private String órgãoJulgador = "";
    private String dataDoJulgamento = "";
    private String dataDaPublicação = "";
    private String ementa = "";
    private String acórdão = "";
    private String notas = "";
    private String outrasInformações = "";
    private String palavrasDeResgate = "";
    private String referenciaLegislativa = "";
    private String doutrina = "";
    private String veja = "";
    private String sucessivos = "";

    /**
     * @return the processo
     */
    public String getProcesso() {
        return processo;
    }

    /**
     * @param processo the processo to set
     */
    public void setProcesso(String processo) {
        this.processo = processo;
    }

    /**
     * @return the relator
     */
    public String getRelator() {
        return relator;
    }

    /**
     * @param relator the relator to set
     */
    public void setRelator(String relator) {
        this.relator = relator;
    }
 /**
     * @return the relatorParaAcordao
     */
    public String getrelatorParaAcordao() {
        return relatorParaAcordao;
    }

    /**
     * @param relatorParaAcordao the relatorParaAcordao to set
     */
    public void setrelatorParaAcordao(String relatorParaAcordao) {
        this.relatorParaAcordao = relatorParaAcordao;
    }
    /**
     * @return the órgãoJulgador
     */
    public String getOrgaoJulgador() {
        return órgãoJulgador;
    }

    /**
     * @param órgãoJulgador the órgãoJulgador to set
     */
    public void setOrgaoJulgador(String órgãoJulgador) {
        this.órgãoJulgador = órgãoJulgador;
    }

    /**
     * @return the dataDoJulgamento
     */
    public String getDataDoJulgamento() {
        return dataDoJulgamento;
    }

    /**
     * @param dataDoJulgamento the dataDoJulgamento to set
     */
    public void setDataDoJulgamento(String dataDoJulgamento) {
        this.dataDoJulgamento = dataDoJulgamento;
    }

    /**
     * @return the dataDaPublicação
     */
    public String getDataDaPublicação() {
        return dataDaPublicação;
    }

    /**
     * @param dataDaPublicação the dataDaPublicação to set
     */
    public void setDataDaPublicação(String dataDaPublicação) {
        this.dataDaPublicação = dataDaPublicação;
    }

    /**
     * @return the ementa
     */
    public String getEmenta() {
        return ementa;
    }

    /**
     * @param ementa the ementa to set
     */
    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    /**
     * @return the acórdão
     */
    public String getAcórdão() {
        return acórdão;
    }

    /**
     * @param acórdão the acórdão to set
     */
    public void setAcórdão(String acórdão) {
        this.acórdão = acórdão;
    }

    /**
     * @return the notas
     */
    public String getNotas() {
        return notas;
    }

    /**
     * @param notas the notas to set
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }

    /**
     * @return the outrasInformações
     */
    public String getOutrasInformações() {
        return outrasInformações;
    }

    /**
     * @param outrasInformações the outrasInformações to set
     */
    public void setOutrasInformações(String outrasInformações) {
        this.outrasInformações = outrasInformações;
    }

    /**
     * @return the palavrasDeResgate
     */
    public String getPalavrasDeResgate() {
        return palavrasDeResgate;
    }

    /**
     * @param palavrasDeResgate the palavrasDeResgate to set
     */
    public void setPalavrasDeResgate(String palavrasDeResgate) {
        this.palavrasDeResgate = palavrasDeResgate;
    }

    /**
     * @return the referenciaLegislativa
     */
    public String getReferenciaLegislativa() {
        return referenciaLegislativa;
    }

    /**
     * @param referenciaLegislativa the referenciaLegislativa to set
     */
    public void setReferenciaLegislativa(String referenciaLegislativa) {
        this.referenciaLegislativa = referenciaLegislativa;
    }

    /**
     * @return the doutrina
     */
    public String getDoutrina() {
        return doutrina;
    }

    /**
     * @param doutrina the doutrina to set
     */
    public void setDoutrina(String doutrina) {
        this.doutrina = doutrina;
    }

    /**
     * @return the veja
     */
    public String getVeja() {
        return veja;
    }

    /**
     * @param veja the veja to set
     */
    public void setVeja(String veja) {
        this.veja = veja;
    }

    /**
     * @return the sucessivos
     */
    public String getSucessivos() {
        return sucessivos;
    }

    /**
     * @param sucessivos the sucessivos to set
     */
    public void setSucessivos(String sucessivos) {
        this.sucessivos = sucessivos;
    }
    
    public void escreve(Writer out) throws IOException {
        //Processo;Relator;Orgao Julgador;Data do Julgamento;Data da Publicação;Ementa;" +
        //"Acórdão;Notas;Outras informações;Palavras de resgate;Referencia legislativa;" +
        //"Doutrina;Veja;Sucessivos
        out.write(getProcesso().replace(';', '|').replaceAll("\\n", "|") + ";");
        out.write(getRelator().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getrelatorParaAcordao().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getOrgaoJulgador().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getDataDoJulgamento().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getDataDaPublicação().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getEmenta().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getAcórdão().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getNotas().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getOutrasInformações().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getPalavrasDeResgate().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getReferenciaLegislativa().replace(';', '|').replaceAll("\\n", "|") + ";");
        out.write(getDoutrina().replace(';', '|').replaceAll("\\n", " ") + ";");
        out.write(getVeja().replace(';', '|').replaceAll("\\n", "|") + ";");
        out.write(getSucessivos().replace(';', '|').replaceAll("\\n", "|") + ";");
    }
}
