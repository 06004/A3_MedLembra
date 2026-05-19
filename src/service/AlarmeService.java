package service;

import java.time.LocalTime;
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

    // Formatador para converter horários de String para LocalTime
    private final FormatadorAlarme formatador = new FormatadorAlarme();

    // Scheduler para agendamento de tarefas
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    // Mapa para manter referência dos alarmes agendados (id -> tarefa)
    private final Map<String, ScheduledFuture<?>> alarmes = new ConcurrentHashMap<>();

    // Método para verificar o medicamento e agendar alarme
    public void agendarMedicamento(String id, Medicamento m, String nomeIdoso) {

        LocalTime horario = formatador.converterHorario(m.getHorario());

        if (horario == null) {
            System.out.println("Horário inválido: " + m.getNome());
            return;
        }

        long delay = calcularDelayEmSegundos(horario);

        AlarmeInfo info = new AlarmeInfo(m, nomeIdoso);

        ScheduledFuture<?> tarefa = scheduler.schedule(() -> {
            System.out.println("⏰ Hora do medicamento: " + info.m.getNome() + " para " + info.nomeIdoso);
            alarmes.remove(id);
        }, delay, TimeUnit.SECONDS);

        alarmes.put(id, tarefa);
    }

    // Método para cancelar um alarme agendado
    public void cancelarAlarme(String id) {
        ScheduledFuture<?> tarefa = alarmes.remove(id);

        if (tarefa != null) {
            tarefa.cancel(false);
            System.out.println("Alarme cancelado: " + id);
        } else {
            System.out.println("Nenhum alarme encontrado para: " + id);
        }
    }

    // Método para calcular o tempo (delay) em segundos até o horário do medicamento
    private long calcularDelayEmSegundos(LocalTime horario) {

        // Obtém o horário atual
        LocalTime agora = LocalTime.now();

        // Converte ambos os horários para segundos do dia
        long segundosAgora = agora.toSecondOfDay();
        long segundosAlvo = horario.toSecondOfDay();

        // Calcula o tempo (delay) em segundos, considerando a possibilidade de o horário alvo ser no dia seguinte
        long delay = segundosAlvo - segundosAgora;

        // Se o horário alvo já passou, ajusta para o próximo dia
        if (delay < 0) {
            delay += 24 * 60 * 60;
        }

        return delay;
    }
}