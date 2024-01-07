/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpus_uniga;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS VIVOBOOK
 */
public class FDataBuku extends javax.swing.JFrame {

    /**
     * Creates new form FDataBuku
     */
    public FDataBuku() {
        initComponents();
        load_data(); //Memanggil Menampilkan Data
        IDOtomatis();
    }

    //Load Data Dari Database tbl_Buku
    private void load_data()
    {
        Connection kon=koneksi.koneksiDb();
        Object header[]={"ID KODE BUKU","KODE REGISTRASI","JUDUL BUKU","NAMA PENGARANG","TAHUN TERBIT","TANGGAL PENGADAAN","SUMBER PENGADAAN","BAHASA","STATUS REFERENSI","KATEGORI"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        TabelBuku.setModel (data);
        String sql_data="SELECT * FROM t_buku";
        try
        {
            Statement st=kon.createStatement();
            ResultSet rs=st.executeQuery(sql_data);
            while(rs.next())
            {
                String d1=rs.getString(1);
                String d2=rs.getString(2);
                String d3=rs.getString(3);
                String d4=rs.getString(4);
                String d5=rs.getString(5);
                String d6=rs.getString(6);
                String d7=rs.getString(7);
                String d8=rs.getString(8);
                String d9=rs.getString(9);
                String d10=rs.getString(10);
                
                
                String d[]={d1,d2,d3,d4,d5,d6,d7,d8,d9,d10};
                data.addRow(d);
            
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    //ID Buku Otomatis 
    private void IDOtomatis()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_id="SELECT * FROM t_buku order by kode_buku desc";
            ResultSet rs=st.executeQuery(sql_id);
            if(rs.next())
            {
                String kode_buku=rs.getString("kode_buku").substring(1);
                String AN=""+(Integer.parseInt(kode_buku)+1);
                String No1="";
                if(AN.length()==1) //Jika id_Buku A00001
                {
                    No1="0000";
                }
                else if(AN.length()==2) //Jika id_anggota A00010
                {
                    No1="000";
                }
                else if(AN.length()==3) //Jika id_anggota A00100
                {
                    No1="00";
                }
                KODEBUKU.setText("B"+No1+AN);
            }
            else
            {
                KODEBUKU.setText("B00001");
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,e);
                }
    }
    
    //INPUT DATA-----------------------------------------------------------
    private void input_data()
    {
        try
        {
        Connection kon=koneksi.koneksiDb();
        Statement st=kon.createStatement(); 
        // Validasi - pastikan semua kolom terisi
        if (KODEBUKU.getText().isEmpty() || KODEREG.getText().isEmpty() || JUDUL.getText().isEmpty() || NAMAPENGARANG.getText().isEmpty() || TAHUNTERBIT.getText().isEmpty()
                || TANGGAL.getDate() == null || SUMBER.getText().isEmpty()
                || BAHASA.getText().isEmpty() || STATUSREF.getText().isEmpty() || KATEGORI.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap isi semua kolom sebelum menambahkan data.");
            return; // Jika ada yang kosong, hentikan eksekusi
        }
        
        // Validasi - memastikan tahun_terbit hanya berisi angka
        String tahunTerbit = TAHUNTERBIT.getText();
        if (!tahunTerbit.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Tahun Terbit hanya boleh berisi angka.");
            return; // Jika tahun_terbit tidak valid, hentikan eksekusi
        }
        
        // Ambil nilai tanggal dari JDateChooser
        Date selectedDate = TANGGAL.getDate();
        

        // Gunakan SimpleDateFormat untuk memformat tanggal
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        String sql="INSERT INTO t_buku values('"+KODEBUKU.getText()
                +"','"+KODEREG.getText()
                +"','"+JUDUL.getText()
                +"','"+NAMAPENGARANG.getText()
                +"','"+TAHUNTERBIT.getText()
                +"','" + formattedDate
                +"','"+SUMBER.getText()
                +"','"+BAHASA.getText()
                +"','"+STATUSREF.getText()
                +"','"+KATEGORI.getText()
                +"')";
        st.execute(sql);
        JOptionPane.showMessageDialog(null, "Data Buku Berhasil Dimasukkan");
        }
        catch(Exception e)
            {
               JOptionPane.showMessageDialog(null, e);
            }
    }
    
     //Reset Data Setelah Input/Edit/Delete
    public void clear()
    {
           KODEREG.setText("");
           JUDUL.setText("");
           NAMAPENGARANG.setText("");
           TAHUNTERBIT.setText("");
           SUMBER.setText("");
           BAHASA.setText("");
           STATUSREF.setText("");
           KATEGORI.setText("");
    }
    
     //Edit Data
    public void update()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(TANGGAL.getDate());
            
             // Validasi - memastikan tahun_terbit hanya berisi angka
            String tahunTerbit = TAHUNTERBIT.getText();
            if (!tahunTerbit.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Tahun Terbit hanya boleh berisi angka.");
            return; // Jika tahun_terbit tidak valid, hentikan eksekusi
            }
            
            String sql_update="UPDATE t_buku SET kode_registrasi='"+KODEREG.getText()
                    +"',judul_buku='"+JUDUL.getText()
                    +"',nama_pengarang='"+NAMAPENGARANG.getText()
                    +"',thn_terbit='"+TAHUNTERBIT.getText()
                    +"',tgl_pengadaan='"+formattedDate
                    +"',sumber_pengadaan='"+SUMBER.getText()
                    +"',bahasa='"+BAHASA.getText()
                    +"',status_ref='"+STATUSREF.getText()
                    +"',kategori='"+KATEGORI.getText()
                    +"' WHERE kode_buku='"+KODEBUKU.getText()+"'";
            st.execute(sql_update);
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     //Delete Data Petugas
    private void delete()
    {
        try
        {
          Connection kon=koneksi.koneksiDb();
          Statement st=kon.createStatement();  
          String sql_delete="DELETE from t_buku WHERE "
                  +"kode_buku='"+KODEBUKU.getText()+"'";
          st.executeUpdate(sql_delete);
          JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        KODEBUKU = new javax.swing.JTextField();
        KODEREG = new javax.swing.JTextField();
        JUDUL = new javax.swing.JTextField();
        NAMAPENGARANG = new javax.swing.JTextField();
        SUMBER = new javax.swing.JTextField();
        BAHASA = new javax.swing.JTextField();
        STATUSREF = new javax.swing.JTextField();
        KATEGORI = new javax.swing.JTextField();
        TANGGAL = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelBuku = new javax.swing.JTable();
        BKELUAR = new javax.swing.JButton();
        BINPUT = new javax.swing.JButton();
        BEDIT = new javax.swing.JButton();
        BDELETE = new javax.swing.JButton();
        TAHUNTERBIT = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("KELOLA DATA BUKU");

        jLabel1.setText("KODE BUKU");

        jLabel2.setText("KODE REGISTRASI");

        jLabel4.setText("JUDUL BUKU");

        jLabel5.setText("NAMA PENGARANG");

        jLabel6.setText("TAHUN TERBIT");

        jLabel7.setText("TANGGAL PENGADAAN");

        jLabel8.setText("SUMBER PENGADAAN");

        jLabel9.setText("BAHASA");

        jLabel10.setText("STATUS REFERENSI");

        jLabel11.setText("KATEGORI");

        KODEBUKU.setEnabled(false);

        TabelBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TabelBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelBuku);

        BKELUAR.setText("KELUAR");
        BKELUAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BKELUARActionPerformed(evt);
            }
        });

        BINPUT.setText("INPUT");
        BINPUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BINPUTActionPerformed(evt);
            }
        });

        BEDIT.setText("EDIT");
        BEDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEDITActionPerformed(evt);
            }
        });

        BDELETE.setText("DELETE");
        BDELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDELETEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(341, 341, 341))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BKELUAR)
                        .addGap(18, 18, 18)
                        .addComponent(BINPUT)
                        .addGap(18, 18, 18)
                        .addComponent(BEDIT)
                        .addGap(18, 18, 18)
                        .addComponent(BDELETE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(KODEREG, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(KODEBUKU, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JUDUL)
                            .addComponent(NAMAPENGARANG)
                            .addComponent(SUMBER)
                            .addComponent(BAHASA)
                            .addComponent(STATUSREF)
                            .addComponent(KATEGORI)
                            .addComponent(TAHUNTERBIT)
                            .addComponent(TANGGAL, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(KODEBUKU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(KODEREG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(JUDUL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(NAMAPENGARANG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(TAHUNTERBIT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(SUMBER)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BAHASA)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(STATUSREF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(KATEGORI)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(TANGGAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BKELUAR)
                    .addComponent(BINPUT)
                    .addComponent(BEDIT)
                    .addComponent(BDELETE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BKELUARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BKELUARActionPerformed
        // TODO add your handling code here:
        int keluar;
        keluar = JOptionPane.showOptionDialog(this,
                "Keluar dari Aplikasi?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(keluar==JOptionPane.YES_NO_OPTION)
        {
        new FUtama() .show();
        this.dispose();
        }  
    }//GEN-LAST:event_BKELUARActionPerformed

    private void BINPUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BINPUTActionPerformed
        // TODO add your handling code here:
    int simpan = JOptionPane.showOptionDialog(this,
                "Apakah Data Sudah Benar?",
                "Simpan Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simpan==JOptionPane.YES_OPTION)
        {
            input_data();
            load_data();
            clear();
            IDOtomatis();
        }     
    }//GEN-LAST:event_BINPUTActionPerformed

    private void TabelBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelBukuMouseClicked
        // TODO add your handling code here:
                // TODO add your handling code here:
        int bar=TabelBuku.getSelectedRow();
        String a=TabelBuku.getValueAt(bar, 0).toString();
        String b=TabelBuku.getValueAt(bar, 1).toString();
        String c=TabelBuku.getValueAt(bar, 2).toString();
        String d=TabelBuku.getValueAt(bar, 3).toString();
        String e=TabelBuku.getValueAt(bar, 4).toString();
        String f=TabelBuku.getValueAt(bar, 5).toString();
        String g=TabelBuku.getValueAt(bar, 6).toString();
        String h=TabelBuku.getValueAt(bar, 7).toString();
        String i=TabelBuku.getValueAt(bar, 8).toString();
        String j=TabelBuku.getValueAt(bar, 9).toString();
        
        KODEBUKU.setText(a);
        KODEREG.setText(b);
        JUDUL.setText(c);
        NAMAPENGARANG.setText(d);
        TAHUNTERBIT.setText(e);
        // Set tanggal pada JCalendar
        try {
            Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(f);
            TANGGAL.setDate(tanggal);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        SUMBER.setText(g);
        BAHASA.setText(h);
        STATUSREF.setText(i);
        KATEGORI.setText(j);
        //Set Disable Input
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true); 
    }//GEN-LAST:event_TabelBukuMouseClicked

    private void BEDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEDITActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
         int update = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan di Update?",
                "Update Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(update==JOptionPane.YES_OPTION)
        {
        update();
        clear();
        load_data();
        IDOtomatis();
        //Set Enable INPUT
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true);
        }
    }//GEN-LAST:event_BEDITActionPerformed

    private void BDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDELETEActionPerformed
        // TODO add your handling code here:
     int delete = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan Di Hapus?",
                "Hapus Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(delete==JOptionPane.YES_NO_OPTION)
        {
        delete();
        clear();
        load_data();
        IDOtomatis();
        //Set Enablet INPUT
        BINPUT.setEnabled(true);
        BEDIT.setEnabled(false);
        BDELETE.setEnabled(false);
        
        }   
    }//GEN-LAST:event_BDELETEActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BAHASA;
    private javax.swing.JButton BDELETE;
    private javax.swing.JButton BEDIT;
    private javax.swing.JButton BINPUT;
    private javax.swing.JButton BKELUAR;
    private javax.swing.JTextField JUDUL;
    private javax.swing.JTextField KATEGORI;
    private javax.swing.JTextField KODEBUKU;
    private javax.swing.JTextField KODEREG;
    private javax.swing.JTextField NAMAPENGARANG;
    private javax.swing.JTextField STATUSREF;
    private javax.swing.JTextField SUMBER;
    private javax.swing.JTextField TAHUNTERBIT;
    private com.toedter.calendar.JDateChooser TANGGAL;
    private javax.swing.JTable TabelBuku;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
