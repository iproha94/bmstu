// include('src/perceptron.js')
function Perceptron(dimension) {
    let self = this;
    
    let learningRate = 0.1

    let weights = [];
    for (let i = 0; i < dimension; ++i) {
        weights[i] = Math.random() * 2 - 1;
    }

    self.response = function(x) {
        let y = 0;

        x.forEach((val, i) => {
            y += val * weights[i];
        })

        return y >= 0 ? 1 : -1;
    }

    self.updateWeights = function(x, err) {
        x.forEach((val, i) => {
            weights[i] += learningRate * err * val;
        })
    }

    self.training = function(data, maxIter) {
        let learned = false;
        let iteration = 0;

        while (!learned) {
            let globalError = 0;

            data.forEach((x) => {
                let r = self.response([x.growth, x.weight]);

                if (x.sex != r) {
                    let err = x.sex - r;
                    self.updateWeights([x.growth, x.weight], err);

                    globalError += Math.abs(err);
                }
            })

            iteration++;

            if (globalError == 0.0 || iteration >= maxIter) {
                learned = true;
                console.log("iteration = ", iteration);
            }
        }
    }
} 

let perceptron;

function training(maxIter, dimension) {
	perceptron = new Perceptron(dimension);

    let data = sourceData.map((val) => {
        return {
            growth: val[0],
            weight: val[1],
            sex: val[2]
        };
    });

	perceptron.training(data, maxIter);

}

function determine(people, maxIter) {
    let sex = perceptron.response([people.growth, people.weight]) == 1 ? 'M' : 'F';
	
    return {
		sex
	}
}

let sourceData = [
    [173, 60, -1],
    [171, 49, -1],
    [170, 74, 1],
    [174, 87, 1],
    [186, 82, 1],
    [185, 75, 1],
    [179, 54, -1],
    [165, 63, -1],
    [172, 59, -1],
    [182, 98, 1],
    [175, 64, -1],
    [165, 46, -1],
    [173, 53, -1],
    [186, 92, 1],
    [171, 53, -1],
    [192, 90, 1],
    [176, 63, -1],
    [167, 43, -1],
    [155, 50, -1],
    [167, 49, -1],
    [170, 47, -1],
    [170, 106, 1],
    [169, 50, -1],
    [165, 50, -1],
    [168, 49, -1],
    [163, 57, -1],
    [173, 52, -1],
    [183, 75, 1],
    [198, 120, 1],
    [157, 53, 1],
]