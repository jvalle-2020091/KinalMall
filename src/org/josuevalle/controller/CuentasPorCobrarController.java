package org.josuevalle.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josuevalle.bean.Administracion;
import org.josuevalle.bean.Cliente;
import org.josuevalle.bean.CuentasPorCobrar;
import org.josuevalle.bean.Locales;
import org.josuevalle.db.Conexion;
import org.josuevalle.system.Principal;

public class CuentasPorCobrarController implements Initializable {
    private enum operaciones{NUEVO, GUARDAR,ACTUALIZAR, NINGUNO}
    private operaciones tipoDeOperacion =operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentasPorCobrar> listaCuentaPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Locales> listaLocales;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentaPorCobrar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNetoPago;
    @FXML private TextField txtEstadoPago;
    @FXML private ComboBox cmbCodAdministracion;
    @FXML private ComboBox cmbCodClientes;
    @FXML private ComboBox cmbCodLocales;
    @FXML private TableView tblCuentasPorCobrar;
    @FXML private TableColumn colCodigoCuentaPorCobrar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colCodAdministracion;
    @FXML private TableColumn colCodCliente;
    @FXML private TableColumn colCodLocal;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCuentasPorCobrar.setItems(getCuentaPorCobrar());
        colCodigoCuentaPorCobrar.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoCuentaPorCobrar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("numeroFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Double>("valorNetoPago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("estadoPago"));
        colCodAdministracion.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoAdministracion"));
        colCodCliente.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoCliente"));
        colCodLocal.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoLocal"));
        cmbCodAdministracion.setItems(getAdministracion());
        cmbCodClientes.setItems(getCliente());
        cmbCodLocales.setItems(getLocales());
    }
    
    public ObservableList<CuentasPorCobrar> getCuentaPorCobrar(){
        ArrayList<CuentasPorCobrar> lista = new ArrayList<CuentasPorCobrar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorCobrar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new CuentasPorCobrar(resultado.getInt("codigoCuentaPorCobrar"), 
                                               resultado.getString("numeroFactura"),
                                               resultado.getInt("anio"),
                                               resultado.getInt("mes"),
                                               resultado.getDouble("valorNetoPago"),
                                               resultado.getString("estadoPago"),
                                               resultado.getInt("codigoAdministracion"),
                                               resultado.getInt("codigoCliente"),
                                               resultado.getInt("codigoLocal")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
                return listaCuentaPorCobrar = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
          if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!= null){
              txtCodigoCuentaPorCobrar.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()));
              txtNumeroFactura.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
              txtAnio.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio()));
              txtMes.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes()));
              txtValorNetoPago.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
              txtEstadoPago.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
              cmbCodAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
              cmbCodClientes.getSelectionModel().select(buscarCliente(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
              cmbCodLocales.getSelectionModel().select(buscarLocal(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
          }
          
    }
    
    

    public ObservableList<Administracion> getAdministracion(){
       ArrayList<Administracion> lista = new ArrayList<Administracion>();
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
           ResultSet resultado = procedimiento.executeQuery();
           while(resultado.next()){
               lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                            resultado.getString("direccion"),
                            resultado.getString("telefono")));
           }
           
       }catch(Exception e){
           e.printStackTrace();
       }
        
        return listaAdministracion = FXCollections.observableArrayList(lista);
        
    }
      
      
      public Administracion buscarAdministracion(int codigoAdministracion){
       Administracion resultado = null;
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
           procedimiento.setInt(1, codigoAdministracion);
           ResultSet registro = procedimiento.executeQuery();
           while (registro.next()){
               resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                             registro.getString("direccion"),
                                             registro.getString("telefono"));
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return resultado;
   }
      
   public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("codigoCliente"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("email"),
                        resultado.getInt("codigoLocal"),
                        resultado.getInt("codigoAdministracion"),
                        resultado.getInt("codigoTipoCliente")             
                ));

                
            }
        }catch(Exception e){
            e.printStackTrace();
            }
        
        return listaCliente = FXCollections.observableArrayList(lista);
    }
    
     public ObservableList<Locales> getLocales(){
      ArrayList<Locales> lista = new ArrayList<Locales>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocal }");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                                                resultado.getDouble("saldoFavor"),
                                                resultado.getDouble("saldoContra"),
                                                resultado.getInt("mesesPendientes"),
                                                resultado.getBoolean("disponibilidad"),
                                                resultado.getDouble("valorLocal"),
                                                resultado.getDouble("valorAdministracion")
                
                
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaLocales = FXCollections.observableArrayList(lista);
    }   
    
     public Locales buscarLocal(int codigoLocal){
         Locales resultado = null; 
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocal(?)}");
             procedimiento.setInt(1, codigoLocal);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
                 resultado = new Locales(registro.getInt("codigoLocal"), 
                                         registro.getDouble("saldoFavor"),
                                         registro.getDouble("saldoContra"),
                                         registro.getInt("mesesPendientes"),
                                         registro.getBoolean("disponibilidad"),
                                         registro.getDouble("valorLocal"),
                                         registro.getDouble("valorAdministracion"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
         return resultado;
     }
     
     public Cliente buscarCliente(int codigoCliente){
         Cliente resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
             procedimiento.setInt(1, codigoCliente);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
                 resultado = new Cliente(registro.getInt("codigoCliente"),
                                         registro.getString("nombresCliente"),
                                         registro.getString("apellidosCliente"),
                                         registro.getString("telefonoCliente"),
                                         registro.getString("direccionCliente"),
                                         registro.getString("email"),
                                         registro.getInt("codigoLocal"),
                                         registro.getInt("codigoAdministracion"),
                                         registro.getInt("codigoTipoCliente"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
         return resultado;
     }
     
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/josuevalle/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josuevalle/images/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                 limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/josuevalle/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/josuevalle/images/eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
   }
    
    public void guardar(){
        CuentasPorCobrar registro = new CuentasPorCobrar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setAnio(Integer.parseInt(txtAnio.getText()));
        registro.setMes(Integer.parseInt(txtMes.getText()));
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setCodigoAdministracion(((Administracion)cmbCodAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoCliente(((Cliente)cmbCodClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Locales)cmbCodLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorCobrar(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setInt(2, registro.getAnio());
            procedimiento.setInt(3, registro.getMes());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setString(5, registro.getEstadoPago());
            procedimiento.setInt(6, registro.getCodigoAdministracion());
            procedimiento.setInt(7, registro.getCodigoCliente());
            procedimiento.setInt(8, registro.getCodigoLocal());
            procedimiento.execute();
            listaCuentaPorCobrar.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
               
    }
    
    public void eliminar(){
       switch(tipoDeOperacion){
           case GUARDAR:
               desactivarControles();
               limpiarControles();
               btnNuevo.setText("Nuevo");
               btnEliminar.setText("Eliminar");
               imgNuevo.setImage(new Image("/org/josuevalle/images/agregar.png"));
               imgEliminar.setImage(new Image("/org/josuevalle/images/eliminar.png"));
               btnEditar.setDisable(false);
               btnReporte.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
           default:
               if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!=null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Cuentas Por Cobrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if (respuesta == JOptionPane.YES_OPTION)
                   try{
                       PreparedStatement procedimiento =    Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentasPorCobrar(?)}");
                       procedimiento.setInt(1,((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar());
                       procedimiento.execute();
                       listaCuentaPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
                       limpiarControles();
                   }catch(Exception e){
                       e.printStackTrace();
                   }   
               }else{
                   JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
               }
       }
   }
    
    public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:
               if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!=null){
                   desactivarComboBox();
                   btnEditar.setText("Actualizar");
                   btnReporte.setText("Cancelar");
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   imgEditar.setImage(new Image("/org/josuevalle/images/actualizar.png"));
                   imgReporte.setImage(new Image("/org/josuevalle/images/cancelar.png"));
                   desactivarComboBox();
                   tipoDeOperacion=operaciones.ACTUALIZAR;
               }else{
                   JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
               }
               
               break;
           case ACTUALIZAR:
                   actualizar();
                   btnNuevo.setDisable(false);
                   btnEliminar.setDisable(false);
                   btnEditar.setText("Editar");
                   btnReporte.setText("Reporte");
                   imgEditar.setImage(new Image("/org/josuevalle/images/editar.png"));
                   imgReporte.setImage(new Image("/org/josuevalle/images/Reporte.png"));
                   limpiarControles();
                   desactivarControles();
                   cargarDatos();
                   tipoDeOperacion = operaciones.NINGUNO;
                   break;
       }
   } 
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorCobrar(?,?,?,?,?,?)}");
            CuentasPorCobrar registro = (CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(Integer.parseInt(txtAnio.getText()));
            registro.setMes(Integer.parseInt(txtMes.getText()));
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setEstadoPago(txtEstadoPago.getText());
            procedimiento.setInt(1, registro.getCodigoCuentaPorCobrar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setInt(3, registro.getAnio());
            procedimiento.setInt(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoPago());
            procedimiento.execute();
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
       switch(tipoDeOperacion){
               case ACTUALIZAR:
                   desactivarControles();
                   limpiarControles();
                   btnNuevo.setDisable(false);
                   btnEliminar.setDisable(false);
                   btnEditar.setText("Editar");
                   btnReporte.setText("Reporte");
                   imgEditar.setImage(new Image("/org/josuevalle/images/editar.png"));
                   imgReporte.setImage(new Image("/org/josuevalle/images/Reporte.png"));
                   tipoDeOperacion = operaciones.NINGUNO;
                  break;
       }
   }
    
    
    public void desactivarControles(){
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNetoPago.setEditable(false);
        txtEstadoPago.setEditable(false);
        cmbCodAdministracion.setDisable(true);
        cmbCodClientes.setDisable(true);
        cmbCodLocales.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);
        cmbCodAdministracion.setDisable(false);
        cmbCodClientes.setDisable(false);
        cmbCodLocales.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoCuentaPorCobrar.clear();
        txtNumeroFactura.clear();
        txtAnio.clear();
        txtMes.clear();
        txtValorNetoPago.clear();
        txtEstadoPago.clear();
        cmbCodAdministracion.setValue(null);
        cmbCodClientes.setValue(null);
        cmbCodLocales.setValue(null);
    }
    
    public void desactivarComboBox(){
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);
        cmbCodAdministracion.setDisable(true);
        cmbCodClientes.setDisable(true);
        cmbCodLocales.setDisable(true);
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaLocales (){
          escenarioPrincipal.ventanaLocales();
    }
    
}
