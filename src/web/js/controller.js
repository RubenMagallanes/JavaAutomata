"use strict";

var testData = {
states:[
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "593465.9452350126f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "840988"
      },
      {
        "type": "double",
        "name": "c",
        "value": "503881.7078694993"
      },
      {
        "type": "double",
        "name": "x",
        "value": "236042.7224896221"
      },
      {
        "type": "double",
        "name": "y",
        "value": "400415.69241506193"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Chris"
      }
    ],
    "id": 0
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "545834.1847449585f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "307476"
      },
      {
        "type": "double",
        "name": "c",
        "value": "779910.664741314"
      },
      {
        "type": "double",
        "name": "x",
        "value": "905930.2563087704"
      },
      {
        "type": "double",
        "name": "y",
        "value": "622513.7987176657"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Shane"
      }
    ],
    "id": 1
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "959267.2489167575f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "275014"
      },
      {
        "type": "double",
        "name": "c",
        "value": "611620.1097071607"
      },
      {
        "type": "double",
        "name": "x",
        "value": "111865.59632195259"
      },
      {
        "type": "double",
        "name": "y",
        "value": "87326.69445361629"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Chris"
      }
    ],
    "id": 2
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "671916.9629283523f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "798081"
      },
      {
        "type": "double",
        "name": "c",
        "value": "355375.20662093227"
      },
      {
        "type": "double",
        "name": "x",
        "value": "732569.5044636034"
      },
      {
        "type": "double",
        "name": "y",
        "value": "817695.6627353886"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Ruben"
      }
    ],
    "id": 3
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "327129.43664103956f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "535381"
      },
      {
        "type": "double",
        "name": "c",
        "value": "268786.2700318384"
      },
      {
        "type": "double",
        "name": "x",
        "value": "443488.70374460594"
      },
      {
        "type": "double",
        "name": "y",
        "value": "529942.0225681951"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Chris"
      }
    ],
    "id": 4
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "861181.4773471198f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "624305"
      },
      {
        "type": "double",
        "name": "c",
        "value": "926764.5029078401"
      },
      {
        "type": "double",
        "name": "x",
        "value": "968188.4870380602"
      },
      {
        "type": "double",
        "name": "y",
        "value": "198845.7992100592"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Will"
      }
    ],
    "id": 5
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "362037.47119126696f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "43189"
      },
      {
        "type": "double",
        "name": "c",
        "value": "29040.194201526483"
      },
      {
        "type": "double",
        "name": "x",
        "value": "796110.6484111304"
      },
      {
        "type": "double",
        "name": "y",
        "value": "454561.55980886426"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Shane"
      }
    ],
    "id": 6
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "531500.3630681641f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "237913"
      },
      {
        "type": "double",
        "name": "c",
        "value": "696534.4422689956"
      },
      {
        "type": "double",
        "name": "x",
        "value": "413418.6485347144"
      },
      {
        "type": "double",
        "name": "y",
        "value": "826064.5779296142"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Nicky"
      }
    ],
    "id": 7
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "710322.8013221944f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "755945"
      },
      {
        "type": "double",
        "name": "c",
        "value": "23854.944586656933"
      },
      {
        "type": "double",
        "name": "x",
        "value": "323621.2728636689"
      },
      {
        "type": "double",
        "name": "y",
        "value": "713170.5944965259"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Chris"
      }
    ],
    "id": 8
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "573873.5593653143f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "129012"
      },
      {
        "type": "double",
        "name": "c",
        "value": "244927.7317678541"
      },
      {
        "type": "double",
        "name": "x",
        "value": "793773.4145642396"
      },
      {
        "type": "double",
        "name": "y",
        "value": "489882.89847495593"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Will"
      }
    ],
    "id": 9
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "125016.4623383715f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "545996"
      },
      {
        "type": "double",
        "name": "c",
        "value": "281697.8970109466"
      },
      {
        "type": "double",
        "name": "x",
        "value": "698930.3119966775"
      },
      {
        "type": "double",
        "name": "y",
        "value": "422235.06731821835"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Ruben"
      }
    ],
    "id": 10
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "60524.37420385326f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "215015"
      },
      {
        "type": "double",
        "name": "c",
        "value": "944631.6606377016"
      },
      {
        "type": "double",
        "name": "x",
        "value": "135188.96332417196"
      },
      {
        "type": "double",
        "name": "y",
        "value": "339736.82747020695"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Chris"
      }
    ],
    "id": 11
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "386563.5365465424f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "217396"
      },
      {
        "type": "double",
        "name": "c",
        "value": "510496.3025103567"
      },
      {
        "type": "double",
        "name": "x",
        "value": "697468.9528924244"
      },
      {
        "type": "double",
        "name": "y",
        "value": "806190.3583432985"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Chris"
      }
    ],
    "id": 12
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "835866.5120630076f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "79962"
      },
      {
        "type": "double",
        "name": "c",
        "value": "549032.3360051912"
      },
      {
        "type": "double",
        "name": "x",
        "value": "773923.2380101299"
      },
      {
        "type": "double",
        "name": "y",
        "value": "77594.88856233143"
      },
      {
        "type": "String",
        "name": "z",
        "value": "Chris"
      }
    ],
    "id": 13
  },
  {
    "fields": [
      {
        "type": "float",
        "name": "a",
        "value": "81857.18153975962f"
      },
      {
        "type": "int",
        "name": "b",
        "value": "943676"
      },
      {
        "type": "double",
        "name": "c",
        "value": "546481.9995326138"
      },
      {
        "type": "double",
        "name": "x",
        "value": "680659.0646656213"
      },
      {
        "type": "double",
        "name": "y",
        "value": "456042.77541082405"
      },
      {
        "type": "String",
        "name": "z",
        "value": "David"
      }
    ],
    "id": 14
  }
],links:[
  {
    "methodName": "add",
    "source": 0,
    "target": 1
  },
  {
    "methodName": "divide",
    "source": 1,
    "target": 2
  },
  {
    "methodName": "mutate",
    "source": 2,
    "target": 3
  },
  {
    "methodName": "add",
    "source": 3,
    "target": 4
  },
  {
    "methodName": "multiply",
    "source": 4,
    "target": 5
  },
  {
    "methodName": "mutate",
    "source": 5,
    "target": 6
  },
  {
    "methodName": "mutate",
    "source": 6,
    "target": 7
  },
  {
    "methodName": "add",
    "source": 7,
    "target": 8
  },
  {
    "methodName": "divide",
    "source": 8,
    "target": 9
  },
  {
    "methodName": "clone",
    "source": 9,
    "target": 10
  },
  {
    "methodName": "add",
    "source": 10,
    "target": 11
  },
  {
    "methodName": "clone",
    "source": 11,
    "target": 12
  },
  {
    "methodName": "add",
    "source": 12,
    "target": 13
  },
  {
    "methodName": "minus",
    "source": 13,
    "target": 14
  }
]

};

var svg = d3.select("svg")
    .attr("width", 1200)
    .attr("height", 800)
    .attr("transform", "translate(0,0)");

automata.viz.init(testData, svg);
