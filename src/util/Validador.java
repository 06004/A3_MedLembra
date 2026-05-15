package util;

public class Validador {

    public static boolean isNotEmpty(String valor) {
        return valor != null && !valor.isBlank();
    }

    public static boolean isIdadeValida(int idade) {
        return idade > 0 && idade <= 130;
    }

    public static boolean isHorarioValido(String horario) {
        if (horario == null || horario.isBlank()) 
        return false;
        
        String[] partes = horario.split(":");
        
        if (partes.length != 2) 
        return false;
        
        try {
            int hora = Integer.parseInt(partes[0]);
            int minuto = Integer.parseInt(partes[1]);
            return hora >= 0 && hora <= 23 && minuto >= 0 && minuto <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}