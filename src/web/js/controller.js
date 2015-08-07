"use strict";

var testData = {
states:[
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "b",
        "name": "int",
        "value": "604213"
      },
      {
        "type": "c",
        "name": "int",
        "value": "788225"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "y",
        "name": "float",
        "value": "114823.1431456167f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "696162.9654105919"
      }
    ],
    "id": 0
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Chris"
      },
      {
        "type": "b",
        "name": "int",
        "value": "12510"
      },
      {
        "type": "c",
        "name": "int",
        "value": "135149"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "y",
        "name": "float",
        "value": "866432.4515396737f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "727804.6050608195"
      }
    ],
    "id": 1
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "b",
        "name": "int",
        "value": "184134"
      },
      {
        "type": "c",
        "name": "int",
        "value": "223981"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "y",
        "name": "float",
        "value": "508999.5217273405f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "716696.3876693207"
      }
    ],
    "id": 2
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Nicky"
      },
      {
        "type": "b",
        "name": "int",
        "value": "955188"
      },
      {
        "type": "c",
        "name": "int",
        "value": "789877"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "y",
        "name": "float",
        "value": "671825.8478214632f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "38271.22395361715"
      }
    ],
    "id": 3
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Chris"
      },
      {
        "type": "b",
        "name": "int",
        "value": "783444"
      },
      {
        "type": "c",
        "name": "int",
        "value": "117143"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Nicky"
      },
      {
        "type": "y",
        "name": "float",
        "value": "670179.0064772968f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "343539.30251097295"
      }
    ],
    "id": 4
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Chris"
      },
      {
        "type": "b",
        "name": "int",
        "value": "374022"
      },
      {
        "type": "c",
        "name": "int",
        "value": "186800"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Chris"
      },
      {
        "type": "y",
        "name": "float",
        "value": "54924.82839690471f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "284282.0333021501"
      }
    ],
    "id": 5
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Nicky"
      },
      {
        "type": "b",
        "name": "int",
        "value": "338125"
      },
      {
        "type": "c",
        "name": "int",
        "value": "523669"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "y",
        "name": "float",
        "value": "376202.63193652313f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "506051.9681675527"
      }
    ],
    "id": 6
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "b",
        "name": "int",
        "value": "797720"
      },
      {
        "type": "c",
        "name": "int",
        "value": "80103"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Nicky"
      },
      {
        "type": "y",
        "name": "float",
        "value": "278275.9905461778f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "568162.6279428483"
      }
    ],
    "id": 7
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "b",
        "name": "int",
        "value": "564967"
      },
      {
        "type": "c",
        "name": "int",
        "value": "408775"
      },
      {
        "type": "x",
        "name": "String",
        "value": "David"
      },
      {
        "type": "y",
        "name": "float",
        "value": "382844.86122039915f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "956906.1902520172"
      }
    ],
    "id": 8
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "b",
        "name": "int",
        "value": "683180"
      },
      {
        "type": "c",
        "name": "int",
        "value": "429110"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "y",
        "name": "float",
        "value": "538714.0150261542f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "781976.5299756442"
      }
    ],
    "id": 9
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "David"
      },
      {
        "type": "b",
        "name": "int",
        "value": "685922"
      },
      {
        "type": "c",
        "name": "int",
        "value": "602360"
      },
      {
        "type": "x",
        "name": "String",
        "value": "David"
      },
      {
        "type": "y",
        "name": "float",
        "value": "128692.59329256922f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "837479.98688686"
      }
    ],
    "id": 10
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "David"
      },
      {
        "type": "b",
        "name": "int",
        "value": "429025"
      },
      {
        "type": "c",
        "name": "int",
        "value": "42016"
      },
      {
        "type": "x",
        "name": "String",
        "value": "David"
      },
      {
        "type": "y",
        "name": "float",
        "value": "613739.0038333248f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "265834.72340017103"
      }
    ],
    "id": 11
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "David"
      },
      {
        "type": "b",
        "name": "int",
        "value": "90409"
      },
      {
        "type": "c",
        "name": "int",
        "value": "414409"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "y",
        "name": "float",
        "value": "165329.0531118702f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "180020.45466116036"
      }
    ],
    "id": 12
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Chris"
      },
      {
        "type": "b",
        "name": "int",
        "value": "274905"
      },
      {
        "type": "c",
        "name": "int",
        "value": "765773"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "y",
        "name": "float",
        "value": "567350.7780586915f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "918644.5668399214"
      }
    ],
    "id": 13
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "b",
        "name": "int",
        "value": "763065"
      },
      {
        "type": "c",
        "name": "int",
        "value": "155589"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "y",
        "name": "float",
        "value": "957933.059410947f"
      },
      {
        "type": "z",
        "name": "double",
        "value": "567614.2775210394"
      }
    ],
    "id": 14
  }
],links:[
  {
    "methodName": "toString",
    "source": 0,
    "target": 7
  },
  {
    "methodName": "minus",
    "source": 1,
    "target": 7
  },
  {
    "methodName": "clone",
    "source": 4,
    "target": 11
  },
  {
    "methodName": "mutate",
    "source": 4,
    "target": 9
  },
  {
    "methodName": "multiply",
    "source": 4,
    "target": 9
  },
  {
    "methodName": "minus",
    "source": 4,
    "target": 5
  },
  {
    "methodName": "add",
    "source": 4,
    "target": 6
  },
  {
    "methodName": "divide",
    "source": 5,
    "target": 11
  },
  {
    "methodName": "multiply",
    "source": 5,
    "target": 6
  },
  {
    "methodName": "clone",
    "source": 5,
    "target": 8
  },
  {
    "methodName": "mutate",
    "source": 5,
    "target": 7
  },
  {
    "methodName": "clone",
    "source": 6,
    "target": 8
  },
  {
    "methodName": "minus",
    "source": 6,
    "target": 14
  },
  {
    "methodName": "mutate",
    "source": 6,
    "target": 13
  },
  {
    "methodName": "multiply",
    "source": 6,
    "target": 7
  },
  {
    "methodName": "toString",
    "source": 6,
    "target": 8
  },
  {
    "methodName": "divide",
    "source": 6,
    "target": 8
  },
  {
    "methodName": "minus",
    "source": 7,
    "target": 9
  },
  {
    "methodName": "clone",
    "source": 7,
    "target": 13
  },
  {
    "methodName": "divide",
    "source": 7,
    "target": 10
  },
  {
    "methodName": "clone",
    "source": 8,
    "target": 11
  },
  {
    "methodName": "clone",
    "source": 9,
    "target": 10
  },
  {
    "methodName": "add",
    "source": 9,
    "target": 14
  },
  {
    "methodName": "divide",
    "source": 9,
    "target": 10
  },
  {
    "methodName": "minus",
    "source": 12,
    "target": 14
  },
  {
    "methodName": "toString",
    "source": 12,
    "target": 14
  },
  {
    "methodName": "toString",
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
