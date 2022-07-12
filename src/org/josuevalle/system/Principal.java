/*
Nombre de Programador: Josué Salvador Valle Jiménez 
carné: 2020091
    Fecha de Creación: 29-04-2021
        Modificaciones:
                05-05-2021
                06-05-2021
                12-05-2021
                13-05-2021
                14-05-2021
                19-05-2021
                20-05-2021
                21-05-2021
                26-05-2021
                27-05-2021
                28-05-2021
                02-06-2021
                03-06-2021
                04-06-2021
                09-06-2021
                10-06-2021
                11-06-2021
                16-06-2021
                22-06-2021
                23-06-2021
                29-06-2021
                30-06-2021
                07-07-2021
                08-07-2021

*/

package org.josuevalle.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josuevalle.controller.AdministracionController;
import org.josuevalle.controller.CargosController;
import org.josuevalle.controller.ClienteController;
import org.josuevalle.controller.CuentaPorPagarController;
import org.josuevalle.controller.CuentasPorCobrarController;
import org.josuevalle.controller.DepartamentosController;
import org.josuevalle.controller.EmpleadosController;
import org.josuevalle.controller.HorariosController;
import org.josuevalle.controller.LocalesController;
import org.josuevalle.controller.LoginController;
import org.josuevalle.controller.MenuPrincipalController;
import org.josuevalle.controller.ProgramadorController;
import org.josuevalle.controller.ProveedorController;
import org.josuevalle.controller.TipoClienteController;
import org.josuevalle.controller.UsuarioController;


public class Principal extends Application {
    private final String  PAQUETE_VISTA = "/org/josuevalle/view/"; // RUTA DE LAS VISTAS 
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall ");
        escenarioPrincipal.getIcons().add(new Image("/org/josuevalle/images/logoKinal.png"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/josuevalle/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
          ventanaLogin();
//        ventanaUsuario();
//        menuPrincipal();
        escenarioPrincipal.show(); 
    }

    public void menuPrincipal(){
        try{
           MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena ("MenuPrincipalView.fxml", 442,435); 
           menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaProgramador(){
        try{
  
            
            ProgramadorController programador =(ProgramadorController)cambiarEscena("ProgramadorView.fxml", 600, 400);
        programador.setEscenarioPrincipal(this);
    }catch(Exception e){
        e.printStackTrace();
    }
        
    }
    
    public void ventanaAdministracion(){
        try{
            AdministracionController admin =(AdministracionController) cambiarEscena("AdministracionView.fxml",812, 416 );
            admin.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaTipoCliente(){
        try{
            TipoClienteController tipoCliente  = (TipoClienteController) cambiarEscena("TipoClienteView.fxml", 857, 418 );
            tipoCliente.setEscenarioPrincipal(this);            
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaLocales(){
        try{
            LocalesController locales = (LocalesController) cambiarEscena("LocalesView.fxml",1001, 438 );
            locales.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDepartamentos(){
        try{
            DepartamentosController departamentos = (DepartamentosController) cambiarEscena("DepartamentoView.fxml", 855, 418);
            departamentos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    public void ventanaClientes(){
        try{
            ClienteController cliente= (ClienteController) cambiarEscena("ClienteView.fxml", 1128, 521);
            cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentasPorPagar(){
        try{
            CuentaPorPagarController cuentasPagar = (CuentaPorPagarController)cambiarEscena("CuentaPorPagarView.fxml",1147,521);
            cuentasPagar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProveedores(){
        try{
            ProveedorController proveedores = (ProveedorController) cambiarEscena("ProveedorView.fxml",1197,521);
            proveedores.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaHorarios(){
        try{
            HorariosController horarios = (HorariosController) cambiarEscena("HorarioView.fxml",1043,521);
            horarios.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }  
    
    public void ventanaUsuario(){
         try{
             UsuarioController usuario = (UsuarioController) cambiarEscena("UsuarioView.fxml",748,386);
             usuario.setEscenarioPrincipal(this);
         }catch(Exception e){
             e.printStackTrace();
         }
    }
    
    public void ventanaLogin(){
    try{
        LoginController loginController = (LoginController) cambiarEscena("LoginView.fxml",490,356);
        loginController.setEscenarioPrincipal(this);
    }catch(Exception e){
        e.printStackTrace();
        }
    }
    
    public void ventanaCargos(){
        try{
            CargosController cargosController = (CargosController) cambiarEscena("CargosView.fxml", 855, 488);
            cargosController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentasPorCobrar(){
        try{
            CuentasPorCobrarController cuentasCobrar = (CuentasPorCobrarController) cambiarEscena("CuentaPorCobrarView.fxml", 1186, 509);
            cuentasCobrar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleados(){
        try{
            EmpleadosController empleados = (EmpleadosController) cambiarEscena("EmpleadosView.fxml", 1359, 533);
            empleados.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene ((AnchorPane)cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        
        return resultado;
    }

}
