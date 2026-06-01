package service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import model.Medicamento;
import util.FormatadorAlarme;

public class AlarmeService {
    private static class AlarmeInfo {
        Medicamento m;
        String nomeIdoso;

        AlarmeInfo(Medicamento m, String nomeIdoso) {
            this.m = m;
            this.nomeIdoso = nomeIdoso;
        }
    }

    private final FormatadorAlarme formatador = new FormatadorAlarme();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private final Map<String, ScheduledFuture<?>> alarmes = new ConcurrentHashMap<>();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // MELHORIA: Recebe lista de histórico para registrar quando o alarme disparar
    public void agendarMedicamento(String id, Medicamento m, String nomeIdoso, List<String> historico) {
        LocalTime horario = formatador.converterHorario(m.getHorario());

        if (horario == null) {
            System.out.println("Horário inválido: " + m.getNome());
            return;
        }

        long delay = calcularDelayEmSegundos(horario);
        AlarmeInfo info = new AlarmeInfo(m, nomeIdoso);

        ScheduledFuture<?> tarefa = scheduler.schedule(() -> {
            String registro = LocalDateTime.now().format(FMT)
                    + " | ⏰ " + info.m.getNome() + " para " + info.nomeIdoso;
            System.out.println("⏰ Hora do medicamento: " + info.m.getNome() + " para " + info.nomeIdoso);

            // MELHORIA: Registra no histórico
            if (historico != null) {
                historico.add(registro);
            }
            alarmes.remove(id);
        }, delay, TimeUnit.SECONDS);

        alarmes.put(id, tarefa);
    }

    public void cancelarAlarme(String id) {
        ScheduledFuture<?> tarefa = alarmes.remove(id);
        if (tarefa != null) {
            tarefa.cancel(false);
            System.out.println("Alarme cancelado: " + id);
        } else {
            System.out.println("Nenhum alarme encontrado para: " + id);
        }
    }

    private long calcularDelayEmSegundos(LocalTime horario) {
        LocalTime agora = LocalTime.now();
        long delay = horario.toSecondOfDay() - agora.toSecondOfDay();
        if (delay < 0) {
            delay += 24 * 60 * 60;
        }
        return delay;
    }
}
