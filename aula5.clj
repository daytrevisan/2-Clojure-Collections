(ns loja.aula5
  (:require [loja.db :as l.db]
            [loja.logic :as l.logic]))

(defn gastou-bastante? [info-do-usuario]
  (> (:preco-total info-do-usuario) 500))

; KEEP
; Equivalente a 'Map' + 'Filter'
(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))
  (println "filter" (filter gastou-bastante? resumo)))

(defn gastou-bastante? [info-do-usuario]
  (println "gastou bastante" (:usuario-id info-do-usuario))
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))
  (println "filter" (filter gastou-bastante? resumo)))

; EAGER -> "guloso"
; LAZY -> "preguiçoso"

(println (range 10))
(println (take 2 (range 100000000000000)))
; Mesmo sendo um range alto, o retorno é rápido
; Pois com a IMUTABILIDADE a sequência é a mesma
; Então, se pedimos os dois primeiros números
; O retorno será rápido => sequência LAZY (e NOT EAGER)

(let [sequencia (range 100000000)]
  (println (take 2 sequencia))
  (println (take 2 sequencia)))



; Gerando um map com "filtro1" + range
(defn filtro1 [x]
  (println "filtro1" x)
  x)

(println (map filtro1 (range 10)))



; Gerando um map com "filtro1" + "filtro2" + range
(defn filtro2 [x]
  (println "filtro2" x)
  x)

; Gera todos do filtro1, depois filtro2 e por último o range com 10 => comportamento EAGER
(println (map filtro2 (map filtro1 (range 10))))



; Mesmo o range sendo o primeiro parâmetro, ele é executado por último => aparentemente EAGER
(->> (range 10)
     (map filtro1)
     (map filtro2)
     println)

; Mas com um range maior, a execução acontece fora de ordem => por CHUNKS
; Comportamento híbrido -> lazy + eager
(->> (range 50)
     (map filtro1)
     (map filtro2)
     println)

; Ao usarmos vetor (mapv)
(->> (range 50)
     (mapv filtro1)
     (mapv filtro2)
     println)

(->> [0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9]
  (map filtro1)
  (map filtro2)
  println)

; LISTA LIGADA -> 100% lazy nesse cenário
; Retorna um de cada
(->> '(0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9)
     (map filtro1)
     (map filtro2)
     println)
