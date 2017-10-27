package rmi;
import java.io.*;

/** Objetos desta classe permitem lêr e escrever em ficheiros de objeto.
 *
 * @author João Afonso Póvoa Marques
 * @author Rita Miguel Fernandes
 * @author Catarina Gonçalves
 */
class FicheirosDeObjeto {
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    /** Abre um ficheiro para leitura
     *
     * @param nomeDoFicheiro O nome do ficheiro para abrir.
     * @throws FileNotFoundException Se o ficheiro nao for encontrado.
     * @throws IOException Se houver erro a ler o ficheiro.
     */
    public void abrirLeitura(String nomeDoFicheiro) throws FileNotFoundException, IOException{
        inputStream = new ObjectInputStream(new	FileInputStream(nomeDoFicheiro));
    }

    /** Lê o objeto do ficheiro aberto
     *
     * @return Retorna o objeto lido.
     * @throws IOException Se houver erro a ler o ficheiro.
     */
    public Object lerObjeto() throws IOException, ClassNotFoundException{
        return inputStream.readObject();
    }

    /** Fecha o ficheiro aberto neste momento
     *
     * @throws IOException Quando ocorre erro de IO
     */
    public void fecharLeitura() throws IOException{
        inputStream.close();
    }

    /** Abre o ficheiro para escrita.
     *
     * @param nomeDoFicheiro O nome do ficheiro para abrir.
     * @throws IOException Quando ocrre erro de IO.
     */
    public void abrirEscrita(String nomeDoFicheiro) throws IOException{
        outputStream = new ObjectOutputStream(new FileOutputStream(nomeDoFicheiro));
    }

    /** Escreve o objeto no ficheiro aberto.
     *
     * @param obejto Objeto para escrever
     * @throws IOException Quando ocorre erro de IO
     */
    public void escreverObjeto(Object obejto) throws IOException{
        outputStream.writeObject(obejto);
    }

    /** Fecha o ficheiro aberto neste momento
     *
     * @throws IOException Quando ocorre erro de IO
     */
    public void fecharEscrita() throws IOException{
        outputStream.close();
    }

}