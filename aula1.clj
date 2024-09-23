(ns loja.aula1)

; ["camila" "guilherme" "diego" "paulo" "lucia" "ana"] -> vetor com vários elementos
; {"guilheme" 37, "paulo" 39} -> mapa associativo/ dicionário
; '(1 2 3 4 5) -> lista ligada
; [[0 1]] -> ou zero ou um como elementos
; #{} -> conjunto

; MAP -> passar pelos elementos de uma coleção aplicando alguma ação
; REDUCE -> reduz para um valor um elemento
; FILTER -> filtra os valores segundo uma condição

(map println ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])

; Implementando com FOR o que o MAP faz

; Pega o primeiro elemento de um vetor e devolve o restante
; Segue esta ideia até terminar o vetor
;println ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]
;println ["guilherme" "diego" "paulo" "lucia" "ana"]
;println ["diego" "paulo" "lucia" "ana"]
;println ["paulo" "lucia" "ana"]
;println ["lucia" "ana"]
;println ["ana"]

; FIRST -> devolve o primeiro elemento de um vetor
(println "primeiro elemento:" (first ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]))
(println "todos exceto o primeiro:" (rest ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]))
(println "próximo elemento:" (next ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]))

; Para uma sequência vazia
(println "rest sequência vazia:" (rest []))                                         ;-> devolve sequência vazia
(println "next sequência vazia:" (next []))                                         ;-> devolve nulo
                                                                                    ; pode ser usado quando a seq acabou

; Um vetor vazio pode ser transformado em uma sequência
; Seq de um vetor vazio é nulo
(println "seq vetor vazio:" (seq []))

; Seq de um vetor com elementos
(println "seq vetor com elementos:" (seq [1 2 3 5]))

; Função 'Meu Mapa'
; Função que aplica 'first' -> busca primeiro elemento
(defn meu-mapa
  [funcao sequencia]                                        ; parâmetros da função "meu mapa"
  (let [primeiro (first sequencia)]                         ; definindo um símbolo que aplica 'first' p/ sequencia
    (funcao primeiro)                                        ; aplica 'funcao' a primeiro
    (meu-mapa funcao (rest sequencia))
    ))

; RECURSÃO
; Função chama a própria linguagem

(println "Imprimindo o primeiro elemento da função 'meu-mapa':")
;(meu-mapa println ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])

; Recursão armazena na memória os símbolos locais p/ poder chamar a próxima invocação, mantendo os já existentes
; Função entra em looping infinito após chegar no último elemento ("ana")
; Isso porque assim que a sequência se torna vazia, tanto o first quanto o rest de vazio é nulo ("nil")



; Estabelecendo condição para terminar o laço quando não tiver mais elemento

; Mapa com parada
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if primeiro
      (do
        (funcao primeiro)
        (meu-mapa funcao (rest sequencia))))))

(println "Com parada:" ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])



; Mapa com parada no nulo ("nil")
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (meu-mapa funcao (rest sequencia))))))

(println "Parada no nil:" ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])
(println "Parada no nil + false:" ["camila" false "carla" "paulo" "lucia" "ana"])
(println "Mapa vazio:" [])
(println "Mapa vazio:" nil)


; Recursão utilizando 'range' p/ intervalo de números
; A recursão demanda muito da memória -> execução retorna erro para ranges altos
(meu-mapa println (range 100000))

; RECUR
; Recursão como recurso nativo ("recur")

; TAIL RECURSION
; Recursão de cauda
; Deve ser a última coisa antes do retorno da função
; Evita o "estouro" do stack
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (recur funcao (rest sequencia))))))                 ; o retorno do "recur" tem que ser o retorno da função

; Se neste exemplo houvesse um 'else', em ambos poderia haver um 'recur'

(meu-mapa println (range 5000))

; Não é TAIL -> pois 'recur' não é o último recurso
;(defn meu-mapa
;  [funcao sequencia]
;  (let [primeiro (first sequencia)]
;    (if (not (nil? primeiro))
;      (do
;        (recur funcao (rest sequencia)
;        (funcao primeiro))))))
;
;(meu-mapa println (range 5000))

; COMPARAÇÃO LAÇO E RECURSÃO

; Uso de laço FOR consome menos memória do que a recursão, pois utiliza-se a mesma variável que vai trocando de valor
; Não estoura a memória, pois não está empilhando a execução (não acumula símbolos locais na memória)
; Porém, a recursão é mais "objetiva"
; O FOR irá demandar o seguinte cenário:
  ; funcao:
  ;   situacao inicial
  ;   for i : 1 to 5000
  ;     outra-variavel =
  ;     outra-outra-variavel =
  ;   situacao final (podendo ter acesso a outras variáveis)
;