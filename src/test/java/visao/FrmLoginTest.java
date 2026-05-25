package visao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FrmLoginTest {

    @Test
    public void deveAceitarUsuarioESenhaPreenchidos() {
        assertDoesNotThrow(() -> FrmLogin.validarCamposLogin("julio.mauro", "Senha123@"));
    }

/*     @Test
    public void deveRecusarUsuarioVazio() {
        MensagemLogin erro = assertThrows(
                MensagemLogin.class,
                () -> FrmLogin.validarCamposLogin("", "1234")
        );

        assertEquals("Por favor, informe o nome de usuário.", erro.getMessage());
    }

    @Test
    public void deveRecusarUsuarioComMenosDeDoisCaracteres() {
        MensagemLogin erro = assertThrows(
                MensagemLogin.class,
                () -> FrmLogin.validarCamposLogin("a", "1234")
        );

        assertEquals("O nome de usuário deve conter ao menos 2 caracteres.", erro.getMessage());
    }

    @Test
    public void deveRecusarSenhaVazia() {
        MensagemLogin erro = assertThrows(
                MensagemLogin.class,
                () -> FrmLogin.validarCamposLogin("admin", "")
        );

        assertEquals("Por favor, informe a senha.", erro.getMessage());
    }*/
}
