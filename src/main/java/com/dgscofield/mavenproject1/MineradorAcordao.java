package com.dgscofield.mavenproject1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Option;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Davidson
 */
public class MineradorAcordao {
    @Option(name="-i", aliases="--data-inicial", usage="Ddata inicial da pesquisa. Formato ddMMyyyy", required=true)
    private String dataInicial;
    
    @Option(name="-f", aliases="--data-final", usage="Data final da pesquisa. Formato ddMMyyyy", required=true)
    private String dataFinal;
    
    @Option(name="-o", aliases="--output-file", usage="Caminho do arquivo de saída")
    private String outFilePathParam;
    
    public void run() throws IOException {
        String outFilePath = outFilePathParam != null ? outFilePathParam : "-";
        
        OutputStream out = null;
        if (outFilePath.equals("-")) {
            out = System.out;
        } else {
            out = Files.newOutputStream(FileSystems.getDefault().getPath(outFilePath), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        
        WebDriver driver = null;
        try(Writer w = new OutputStreamWriter(out, Charset.defaultCharset());
                PrintWriter writer = new PrintWriter(w)) {
            
            writer.println("Processo;Relator;Relator para o Acordao;Orgao Julgador;Data do Julgamento;Data da PublicaÃ§Ã£o;Ementa;" +
                    "AcÃ³rdÃ£o;Notas;Outras informaÃ§Ãµes;Palavras de resgate;Referencia legislativa;" +
                    "Doutrina;Veja;Sucessivos");
            
            Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
            System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, FileSystems.getDefault().getPath("phantomjs.exe").toString());
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            String[] phantomJSArgs = new String[] {"--webdriver-loglevel=NONE"};
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomJSArgs);
            driver = new PhantomJSDriver(capabilities);
            driver.manage().window().maximize();
            driver.get("http://www.stj.jus.br/SCON/");
            //driver.findElement(By.id("pesquisaLivre")).clear();
            //driver.findElement(By.name("pesquisaLivre")).sendKeys("LEASING E DOLAR");
            driver.findElement(By.name("data_inicial")).clear();
            driver.findElement(By.name("data_inicial")).sendKeys(dataInicial);
            driver.findElement(By.name("data_final")).clear();
            driver.findElement(By.name("data_final")).sendKeys(dataFinal);
            new Select(driver.findElement(By.name("tipo_data"))).selectByVisibleText("Publicação");
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            List<WebElement> listCheckBoxes = driver.findElements(By.name("b"));
            listCheckBoxes.stream().forEach((chkbox) -> {
                if(chkbox.getAttribute("value").equalsIgnoreCase("ACOR")) {
                    jsExecutor.executeScript("arguments[0].checked = true", chkbox);
                }
                else {
                    jsExecutor.executeScript("arguments[0].checked = false", chkbox);
                }
            });

            driver.findElement(By.cssSelector("input[type='submit']")).click();
            try {
                driver.findElement(By.xpath("//div[@id='resumopesquisa']/div[2]/span[2]/a")).click();
            } catch (NoSuchElementException ex) {
                //ignore and move on...
            }
            WebElement botaoProx = null;
            do {
                if(botaoProx != null)
                    botaoProx.click();

                List<WebElement> botoes = driver.findElements(By.className("iconeProximaPagina"));
                botaoProx = botoes.isEmpty() ? null : botoes.get(1);

                WebElement listaMaster = driver.findElement(By.id("listadocumentos"));
                
                List<WebElement> tabelasDocumento = listaMaster.findElements(By.xpath("div"));
                
                tabelasDocumento.stream().forEach((tabelaDocumento) -> {
                    List<WebElement> paragrafos = tabelaDocumento.findElements(By.className("paragrafoBRS"));
                    Processo processo = new Processo();
                    paragrafos.stream().forEach((paragrafo) -> {
                        String titulo = paragrafo.findElement(By.className("docTitulo")).getText();
                        String texto = paragrafo.findElement(By.className("docTexto")).getText();
                        
                        if(StringUtils.containsIgnoreCase(titulo, "Processo")) {
                            processo.setProcesso(texto);
                        } else if(StringUtils.equalsIgnoreCase(titulo, "Relator(a)")) {
                            processo.setRelator(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Relator(a) p/")) {
                            processo.setRelatorParaAcordao(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Órgão Julgador")) {
                            processo.setOrgaoJulgador(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Data do julgamento")) {
                            processo.setDataDoJulgamento(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Data da Publicação")) {
                            processo.setDataDaPublicação(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Ementa")) {
                            processo.setEmenta(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Acórdão")) {
                            processo.setAcórdão(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Notas")) {
                            processo.setNotas(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Outras inform")) {
                            processo.setOutrasInformações(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Palavras de resg")) {
                            processo.setPalavrasDeResgate(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Referência legislativa")) {
                            processo.setReferenciaLegislativa(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Doutrina")) {
                            processo.setDoutrina(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Veja")) {
                            processo.setVeja(texto);
                        } else if(StringUtils.containsIgnoreCase(titulo, "Sucessivo")) {
                            Pattern regex = Pattern.compile("(\\d++) documentos");
                            Matcher matcher = regex.matcher(paragrafo.getText());
                            if(matcher.find()) {
                                String qtdSucessivos = matcher.group();
                                processo.setSucessivos(qtdSucessivos + " sucessivos");
                            } else {
                                int qtdSucessivos = paragrafo.findElements(By.className("docTexto")).size();
                                String sucessivos = Integer.toString(qtdSucessivos);// + " sucessivo";
                                //if(qtdSucessivos != 1) {
                               //     sucessivos += "s";
                               // }
                                processo.setSucessivos(sucessivos);
                            }
                        }
                    });
                    try {
                        processo.escreve(writer);
                        writer.println();
                        writer.flush();
                    } catch(IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            } while(botaoProx != null);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
