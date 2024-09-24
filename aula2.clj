(ns loja.aula2)

; ARIDADE E LOOPS

; ["alana" "guilherme" "diego" "paulo" "lucia" "ana"]

(defn conta
  [total-ate-agora elementos]
  (recur (inc total-ate-agora) (rest elementos)))

; Imprime chamando função 'conta' e passando os parâmetros ('total-até-agora' e 'elementos')
; Como é o primeiro -> 'total-ate-agora' inicia em zero
;(println (conta 0 ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]))

; Neste caso, como não há critério de parada, não haverá retorno

; Verificando se há próximo elemento

; Adicionando critério de parada
; Se cair no 'else' e não tiver implementado -> retorna 'nil'
(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))))

(println (conta 0 ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]))

; Implementando com 'else' (implícito)
(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta 0 ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])) ; retorna 5

; A implementação da função terá retorno com um elemento a menos
; Mostrando saída explícita (primeira saída como vetor, demais como sequência)
; Função chega em 'ana' e para de contar

(defn conta
  [total-ate-agora elementos]
  ;(println [total-ate-agora elementos])
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta 0 ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])) ; retorna 5
(println "vetor vazio:" (conta 0 []))                       ; retorna 0

; Corrigindo erro incrementando o retorno de else
(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))
    (inc total-ate-agora)))

(println (conta 0 ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])) ; retorna 6
(println "vetor vazio:" (conta 0 []))                                    ; retorna 1, por causa do incremento no else

; Testando com SEQ -> pede retorno da sequência
; Pergunta se tem elementos (ao invés de perguntar se tem próximo)
; Se for vazio -> devolve nulo; se houver elemento -> devolve a própria sequência

(defn conta
  [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta 0 ["camila" "guilherme" "diego" "paulo" "lucia" "ana"])) ; retorna 6
(println "vetor vazio:" (conta 0 []))                                    ; retorna 0

; Criando uma função para exemplificar duas variações (passando 1 ou 2 parâmetros)
; Conforme o número de parâmetros que é passado, a função sabe qual deve ser o retorno
(defn minha-funcao
  ([parametro1] (println "p1" parametro1))
  ([parametro1 parametro2] (println "p2" parametro1 parametro2)))
(minha-funcao 1)
(minha-funcao 1 2)

; Redefinindo a função 'conta' utilizando possibilidade de duas variações (um ou dois argumentos)
(defn conta

  ; Case 1 -> recebe só um argumento ('elementos') e chama 'conta' passando inicializador 0 e
  ; o vetor 'elementos'
  ([elementos]
   (conta 0 elementos))

  ; Case 2 -> recebe 'total-ate-agora' (inicializador) e vetor 'elementos'
  ; Em seguida, faz validação com if e chama sequência
  ([total-ate-agora elementos]
   (println "antes da recorrência")
   (if (seq elementos)
     (recur (inc total-ate-agora) (next elementos))
     total-ate-agora)))

; Com a função redefinida, não precisamos mais passar o número inicial (0)
(println "função redefinida" (conta ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]))
(println "vetor vazio:" (conta []))

; Utilizando LOOP

(defn conta
  [elementos]
  (println "antes do loop")
  (loop [total-ate-agora 0                                  ; Chama o contador passando a inicialização (0)
         elementos-restantes elementos]                     ; Chama os restantes passando o inicial (elementos)
    (if (seq elementos-restantes)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))

; Função 'conta' com LOOP e RECUR
; 1. Passamos 'elementos' como parâmetro da função
; 2. Em LOOP -> chama 'total-ate-agora' (contador) passando a inicialização e 'elementos-restantes' passando 'elementos'
; como parâmetro inicial
; 3. Se (IF) houver sequência -> vai chamar a volta ao loop, incrementando o contador e chamando o próximo ('next')
; através do RECUR
(println "função com loop" (conta ["camila" "guilherme" "diego" "paulo" "lucia" "ana"]))
;(println "vetor vazio:" (conta []))
