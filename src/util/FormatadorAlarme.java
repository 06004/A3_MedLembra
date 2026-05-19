package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatadorAlarme {
    public LocalTime converterHorario(String horario) {

        horario = horario.trim(); // Remove espaços em branco no início e no fim

        // Verifica se o horário não contém o caractere ":"
        if (!horario.contains(":")) { 

            // Se o horário tiver três caracteres (930), passa para o formato correto (09:30)
            if (horario.length() == 3) {

                horario = "0" + horario.charAt(0) +
                        ":" + horario.substring(1);

            // Se o horário tiver quatro caracteres (1430), passa para o formato correto (14:30)            
            } else if (horario.length() == 4) {

                horario = horario.substring(0, 2) +
                        ":" + horario.substring(2);

            } else {
                return null;
            }
        }

        try {
            // Define o formato esperado para o horário (H:mm) e tenta converter a string (horario) para LocalTime
            DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("H:mm");

            // Se a conversão for bem-sucedida, retorna o objeto LocalTime correspondente ao horário fornecido (horario)
            return LocalTime.parse(horario, formatacao);

        } catch (Exception e) {
            return null;
        }
    }
}
