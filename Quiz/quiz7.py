def lps_array(pattern):
    length = 0
    i = 1
    m = len(pattern)
    lps = [0] * m

    while i < m:
        if pattern[i] == pattern[length]:
            length += 1
            lps[i] = length
            i += 1
        else:
            if length != 0:
                length = lps[length - 1]
            else:
                lps[i] = 0
                i += 1

    return lps

def busca_kmp(text, pattern):
    comparisons = 0
    n = len(text)
    m = len(pattern)
    lps = lps_array(pattern)
    i = j = 0

    while i < n:
        comparisons += 1
        if pattern[j] == text[i]:
            i += 1
            j += 1
        if j == m:
            print(f"Padrão encontrado! Posição: {i - j}")
            j = lps[j - 1]
        elif i < n and pattern[j] != text[i]:
            if j != 0:
                j = lps[j - 1]
            else:
                i += 1

    print(f"Número de comparações realizadas: {comparisons}")

text = "AAABAABBBABAAABA"
pattern = "ABB"

busca_kmp(text, pattern)
