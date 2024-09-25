(ns loja.aula3
  (:require [loja.db :as l.db]))

(println (l.db/todos-os-pedidos))

; Agrupando os pedidos por usuário
(println (group-by :usuario (l.db/todos-os-pedidos)))

(defn minha-funcao-de-agrupamento
  [elemento]
  (println "elemento" elemento)
  (:usuario elemento))

; Traz todos os pedidos por usuário (todos do user 15, depois do 1, 10 e 20)
(println "agrupamento:" (group-by minha-funcao-de-agrupamento (l.db/todos-os-pedidos)))

; { 15 []
;   1  []
;   10 []
;   20 [] }

; Retorna os valores de 'usuário'
(println "valores:" (vals (group-by :usuario (l.db/todos-os-pedidos))))

; COUNT > VALS > GROUP-BY -> Conta o número de usuários totais
(println "número de usuários:" (count (vals (group-by :usuario (l.db/todos-os-pedidos)))))

; MAP > COUNT > VALS > GROUP-BY -> Retorna o somatório dos pedidos por usuário
(println "número de pedidos por usuário:" (map count (vals (group-by :usuario (l.db/todos-os-pedidos)))))

; Usando trad last para o último argumento
; Traz apenas o conteúdo da direita (valores) -> 'vals'
; 'Map' -> p/ fazer o loop e 'Count' p/ trazer o somatório
(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     vals
     (map count)
     (println "conta e retorna o número de pedidos:"))

; Trazendo os argumentos de chave-valor
(defn conta-total-por-usuario
  [[usuario pedidos]]                                       ; destructuring
  (count pedidos))

; Retornando 'usuário' e seus respectivos pedidos
; Chave: 'usuário'/ Valor: 'pedido'
(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)



; Trazendo os argumentos de chave-valor
(defn conta-total-por-usuario
  [[usuario pedidos]]                                       ; destructuring
  [usuario (count pedidos)])

; Retornando vetor com usuário e seus respectivos pedidos
; Chave: 'usuário'/ Valor: 'pedido'
(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     (println "retorno com vetor"))



; Retornando um mapa
; Trazendo os argumentos de chave-valor
(defn conta-total-por-usuario
  [[usuario pedidos]]
  { :usuario-id usuario
   :total-de-pedidos (count pedidos)})

; Retornando chave e valor
; Chave: 'usuário'/ Valor: 'pedido'
(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     (println "usuário com seus pedidos: "))



; Retornando usuário, total de pedidos e preço total dos pedidos
(println "PEDIDOS")

(defn total-do-item
  [[item-id detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))

(defn total-do-pedido
  [pedido]
  (->> pedido
       (map total-do-item)
       (reduce +)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))

(defn quantia-de-pedidos-e-gasto-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id usuario
   :total-de-pedidos (count pedidos)
   :preco-total (total-dos-pedidos pedidos)})

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuario)
     println)