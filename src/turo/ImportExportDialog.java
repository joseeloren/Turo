/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turo;

import java.awt.image.ImageFilter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Jose
 */
public class ImportExportDialog extends JDialog {
    private static final int BUFFER = 2048;
    
    public ImportExportDialog(final MainFrame frame, int mode) {
        super(frame, true);
        String texto = (mode == 2) ? "Importar" : "Exportar";
        setTitle(texto);
        File currentDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        fileChooser.setApproveButtonText(texto);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.tur", "tur"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter turfilter = new FileNameExtensionFilter(
                "tur files (*.tur)", "tur");
        fileChooser.addChoosableFileFilter(turfilter);

        if (mode == 1) {
            fileChooser.setSelectedFile(new File("exportacion_turo.tur"));
        }
        int returnVal = (mode == 2) ? fileChooser.showOpenDialog(frame) : fileChooser.showSaveDialog(frame);

        String dir = System.getProperty("user.home") + "/Turo/";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (mode == 2) { //Importar
                try {
                    BufferedOutputStream dest = null;
                    FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
                    ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
                    ZipEntry entry;
                    while ((entry = zis.getNextEntry()) != null) {
                        int count;
                        byte data[] = new byte[BUFFER];
                        // write the files to the disk
                        FileOutputStream fos = new FileOutputStream(dir + entry.getName());
                        dest = new BufferedOutputStream(fos, BUFFER);
                        while ((count = zis.read(data, 0, BUFFER))
                                != -1) {
                            dest.write(data, 0, count);
                        }
                        dest.flush();
                        dest.close();
                    }
                    zis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else { //Exportar
                try {
                    BufferedInputStream origin = null;
                    FileOutputStream dest = new FileOutputStream(fileChooser.getSelectedFile());
                    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
                    //out.setMethod(ZipOutputStream.DEFLATED);
                    byte data[] = new byte[BUFFER];
                    // get a list of files from current directory
                    
                    File f = new File(dir);
                    String files[] = f.list();

                    for (int i = 0; i < files.length; i++) {
                        System.out.println("Adding: " + files[i]);
                        FileInputStream fi = new FileInputStream(files[i]);
                        origin = new BufferedInputStream(fi, BUFFER);
                        ZipEntry entry = new ZipEntry(files[i]);
                        out.putNextEntry(entry);
                        int count;
                        while ((count = origin.read(data, 0,
                                BUFFER)) != -1) {
                            out.write(data, 0, count);
                        }
                        origin.close();
                    }
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }

        }

        getContentPane().add(fileChooser);
        pack();

        setLocationRelativeTo(null);
    }

}
