#include <algorithm>
#include <cctype>
#include <iostream>
#include <string>
#include <cstring>


void printASCIIART() {

  std::cout << R"(
       ::::::::      :::     :::::::::: ::::::::      :::     :::::::::  ::: ::::::::             :::::::: ::::::::::: :::::::::  :::    ::: :::::::::: ::::::::: 
    :+:    :+:   :+: :+:   :+:       :+:    :+:   :+: :+:   :+:    :+: :+ :+:    :+:           :+:    :+:    :+:     :+:    :+: :+:    :+: :+:        :+:    :+: 
   +:+         +:+   +:+  +:+       +:+         +:+   +:+  +:+    +:+    +:+                  +:+           +:+     +:+    +:+ +:+    +:+ +:+        +:+    +:+  
  +#+        +#++:++#++: +#++:++#  +#++:++#++ +#++:++#++: +#++:++#:     +#++:++#++           +#+           +#+     +#++:++#+  +#++:++#++ +#++:++#   +#++:++#:    
 +#+        +#+     +#+ +#+              +#+ +#+     +#+ +#+    +#+           +#+           +#+           +#+     +#+        +#+    +#+ +#+        +#+    +#+    
#+#    #+# #+#     #+# #+#       #+#    #+# #+#     #+# #+#    #+#    #+#    #+#           #+#    #+#    #+#     #+#        #+#    #+# #+#        #+#    #+#     
########  ###     ### ########## ########  ###     ### ###    ###     ########             ######## ########### ###        ###    ### ########## ###    ###     
  )" << std::endl;
}


int main (int argc, char *argv[]) {

  printASCIIART();
  std::cout << "Give the word that you want to encrypt: \n";
  std::string sentence{ };
  std::getline(std::cin, sentence);
  std::cout << "Give the value of the offset: \n";
  int offset { };
  std::cin >> offset;
  std::transform(sentence.begin(), sentence.end(), sentence.begin(), ::tolower);




  int length = sentence.length();
  char* sentence_array = new char[length + 1];
  strcpy(sentence_array, sentence.c_str());
  for (int i = 0; i < length; i++){
    if (sentence_array[i] < 123 && sentence_array[i] > 96){
      char offset_character = 97 + ((sentence_array[i] - 97 + offset)%26); 
      sentence_array[i] = offset_character;
    }
  }
  std::cout << sentence_array << '\n';

}



