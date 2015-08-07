"use strict";

var testData = {
states:[
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "692188"
      },
      {
        "type": "b",
        "name": "int",
        "value": "435851"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Nicky"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "y",
        "name": "float",
        "value": "310627.66783672024f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Nicky"
      }
    ],
    "id": 0
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "117961"
      },
      {
        "type": "b",
        "name": "int",
        "value": "814260"
      },
      {
        "type": "c",
        "name": "String",
        "value": "David"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Nicky"
      },
      {
        "type": "y",
        "name": "float",
        "value": "947661.9589985204f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Will"
      }
    ],
    "id": 1
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "715663"
      },
      {
        "type": "b",
        "name": "int",
        "value": "37318"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Chris"
      },
      {
        "type": "y",
        "name": "float",
        "value": "860104.4504056924f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Shane"
      }
    ],
    "id": 2
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "7730"
      },
      {
        "type": "b",
        "name": "int",
        "value": "88550"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "y",
        "name": "float",
        "value": "363098.21154984477f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Chris"
      }
    ],
    "id": 3
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "214219"
      },
      {
        "type": "b",
        "name": "int",
        "value": "260080"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "y",
        "name": "float",
        "value": "365375.9997025284f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Shane"
      }
    ],
    "id": 4
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "63203"
      },
      {
        "type": "b",
        "name": "int",
        "value": "401009"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "y",
        "name": "float",
        "value": "472412.95774932846f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Shane"
      }
    ],
    "id": 5
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "290561"
      },
      {
        "type": "b",
        "name": "int",
        "value": "751895"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "x",
        "name": "String",
        "value": "David"
      },
      {
        "type": "y",
        "name": "float",
        "value": "104713.35996175423f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Chris"
      }
    ],
    "id": 6
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "35748"
      },
      {
        "type": "b",
        "name": "int",
        "value": "410992"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Nicky"
      },
      {
        "type": "y",
        "name": "float",
        "value": "92354.95803827143f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Ruben"
      }
    ],
    "id": 7
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "52051"
      },
      {
        "type": "b",
        "name": "int",
        "value": "739059"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "x",
        "name": "String",
        "value": "David"
      },
      {
        "type": "y",
        "name": "float",
        "value": "855477.4192930905f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Nicky"
      }
    ],
    "id": 8
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "736926"
      },
      {
        "type": "b",
        "name": "int",
        "value": "133659"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Ruben"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "y",
        "name": "float",
        "value": "763132.1668303936f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Chris"
      }
    ],
    "id": 9
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "209092"
      },
      {
        "type": "b",
        "name": "int",
        "value": "763820"
      },
      {
        "type": "c",
        "name": "String",
        "value": "David"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Chris"
      },
      {
        "type": "y",
        "name": "float",
        "value": "369848.7740714994f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Nicky"
      }
    ],
    "id": 10
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "194781"
      },
      {
        "type": "b",
        "name": "int",
        "value": "521429"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "y",
        "name": "float",
        "value": "672330.7751775571f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "David"
      }
    ],
    "id": 11
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "188396"
      },
      {
        "type": "b",
        "name": "int",
        "value": "633388"
      },
      {
        "type": "c",
        "name": "String",
        "value": "David"
      },
      {
        "type": "x",
        "name": "String",
        "value": "David"
      },
      {
        "type": "y",
        "name": "float",
        "value": "191661.0890969316f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "David"
      }
    ],
    "id": 12
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "997326"
      },
      {
        "type": "b",
        "name": "int",
        "value": "901846"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Will"
      },
      {
        "type": "x",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "y",
        "name": "float",
        "value": "874939.7382570703f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "David"
      }
    ],
    "id": 13
  },
  {
    "fields": [
      {
        "type": "a",
        "name": "int",
        "value": "313023"
      },
      {
        "type": "b",
        "name": "int",
        "value": "259097"
      },
      {
        "type": "c",
        "name": "String",
        "value": "Shane"
      },
      {
        "type": "x",
        "name": "String",
        "value": "David"
      },
      {
        "type": "y",
        "name": "float",
        "value": "501929.4218893297f"
      },
      {
        "type": "z",
        "name": "String",
        "value": "Shane"
      }
    ],
    "id": 14
  }
],links:[
  {
    "methodName": "divide",
    "source": 0,
    "target": 1
  },
  {
    "methodName": "clone",
    "source": 1,
    "target": 2
  },
  {
    "methodName": "divide",
    "source": 2,
    "target": 3
  },
  {
    "methodName": "divide",
    "source": 3,
    "target": 4
  },
  {
    "methodName": "clone",
    "source": 4,
    "target": 5
  },
  {
    "methodName": "minus",
    "source": 5,
    "target": 6
  },
  {
    "methodName": "divide",
    "source": 6,
    "target": 7
  },
  {
    "methodName": "clone",
    "source": 7,
    "target": 8
  },
  {
    "methodName": "mutate",
    "source": 8,
    "target": 9
  },
  {
    "methodName": "minus",
    "source": 9,
    "target": 10
  },
  {
    "methodName": "minus",
    "source": 10,
    "target": 11
  },
  {
    "methodName": "clone",
    "source": 11,
    "target": 12
  },
  {
    "methodName": "divide",
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
