(ns loja.aula4
  (:require [loja.db :as l.db]
            [loja.logic :as l.logic]))

(println (l.db/todos-os-pedidos))

; Para não ter que chamar o caminho completo toda vez que for utilizar, declaramos símbolos: 'pedidos' e 'resumo'
; 'pedidos' é uma função que não recebe parâmetros, logo, ela é apenas chamada (e tem o caminho referenciado)
; 'resumo' é uma função que recebe parâmetro

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]

  (println "Resumo" resumo)
  (println "Ordenado" (sort-by :preco-total resumo))
  (println "Ordenado ao contrário" (reverse (sort-by :preco-total resumo)))
  (println "Ordenado por ID" (sort-by :usuario-id resumo))

  ; 'get-in' para entrar na granularidade: itens > mochila > quantidade --> traz o valor referente ao pedido1
  (println "Qtd mochila do primeiro pedido:" (get-in pedidos [0 :itens :mochila :quantidade])))

(defn resumo-por-usuario-ordenado [pedidos]
  (->> pedidos
       (l.logic/resumo-por-usuario)
       (sort-by :preco-total)
       reverse))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (resumo-por-usuario-ordenado pedidos)]
     (println "Resumo" resumo)
     (println "Primeiro" (first resumo))
     (println "Segundo" (second resumo))
     (println "Resto" (rest resumo))                        ; retorna todos, exceto o primeiro
     (println "Total" (count resumo))
     (println "Class" (class resumo))                       ; é uma lista (no começo foi um vetor)
     (println "nth 1" (nth resumo 1))
     (println "get 1" (get resumo 1))                       ; 'get' busca por índice, mas não temos mais vetor
     (println "take 2" (take 2 resumo))                     ; 'take' retorna os n elementos do resumo
     )

; Take direto ou dentro da função 'top-2' --> mesmo retorno
(defn top-2 [resumo]
  (take 2 resumo))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (resumo-por-usuario-ordenado pedidos)]
  (println "Resumo" resumo)
  (println "Top-2" (top-2 resumo)))

; FILTER + LAMBDA
; Filtrando os usuários com preço total maior que 500
(let [pedidos (l.db/todos-os-pedidos)
      resumo (resumo-por-usuario-ordenado pedidos)]
  (println "> 500 filter =>" (filter #(> (:preco-total %) 500) resumo))

  ; SOME
  ; Função booleana
  ; "Será que alguém gastou mais de 500?"
  (println "> 500 some =>" (some #(> (:preco-total %) 500) resumo))

  ; 'Not' + 'Empty' => Equivalência do 'Some' -> mesmo retorno
  ; Filtra pela condição & aqueles que não forem vazios
  (println "> 500 filter empty not =>" (not (empty? (filter #(> (:preco-total %) 500) resumo))))

  ; Testando somente com filter
  (defn pedido-acima-de-500? [pedido]
    (> (:preco-total pedido) 500))

  (println "testando somente filter =>" (filter pedido-acima-de-500? resumo))

  )


