#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>
#include <time.h>
#include <unistd.h>

#define NUM_HILOS 5

bool es_primo(int n) {
    if (n <= 1) return false;
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}

// Función de cada hilo
void* procesar_numero(void* arg) {
    int num = rand() % 100 + 1; // número aleatorio entre 1 y 100

    // Identificar par o impar
    if (num % 2 == 0)
        printf("[Hilo %ld] El hilo numero %d es PAR\n", pthread_self(), num);
    else
        printf("[Hilo %ld] El hilo numero %d es IMPAR\n", pthread_self(), num);

    // Verificar si es primo
    if (es_primo(num))
        printf("[Hilo %ld] El hilo numero %d es PRIMO\n", pthread_self(), num);
    else
        printf("[Hilo %ld] El hilo numero %d NO es primo\n", pthread_self(), num);

    pthread_exit(NULL);
}

int main() {
    srand(time(NULL)); // inicializar semilla aleatoria
    pthread_t hilos[NUM_HILOS];

    // Crear hilos
    for (int i = 0; i < NUM_HILOS; i++) {
        pthread_create(&hilos[i], NULL, procesar_numero, NULL);
    }

    // Se espera a que cada hilo termine
    for (int i = 0; i < NUM_HILOS; i++) {
        pthread_join(hilos[i], NULL);
    }

    printf("Procesamiento finalizado.\n");
    return 0;
}