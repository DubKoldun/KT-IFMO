#include <iostream>
#include <vector>

using std::vector;
using std::cout;
using std::cin;


int main() {
    std::string s;
    cin >> s;

    vector <size_t> pref_func(s.size());

    for (size_t i = 1; i < pref_func.size(); ++i) {
        size_t j = pref_func[i-1];
        while (j > 0 && s[i] != s[j]) {
            j = pref_func[j-1];
        }
        if (s[i] == s[j]) ++j;
        pref_func[i] = j;
    }

    size_t size = s.size();
    if (size % (size - pref_func[size-1]) == 0) {
        cout << size - pref_func[size-1];
    } else {
        cout << size;
    }

    return 0;
}