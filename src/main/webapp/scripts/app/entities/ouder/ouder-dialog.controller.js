'use strict';

angular.module('tiimspotApp').controller('OuderDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Ouder', 'Persoon', 'Comment',
        function($scope, $stateParams, $modalInstance, $q, entity, Ouder, Persoon, Comment) {

        $scope.ouder = entity;
        $scope.persoons = Persoon.query();
        $scope.comments = Comment.query();
        $scope.persoons = Persoon.query({filter: 'ouder-is-null'});
        $q.all([$scope.ouder.$promise, $scope.persoons.$promise]).then(function() {
            if (!$scope.ouder.persoon || !$scope.ouder.persoon.id) {
                return $q.reject();
            }
            return Persoon.get({id : $scope.ouder.persoon.id}).$promise;
        }).then(function(persoon) {
            $scope.persoons.push(persoon);
        });
        $scope.load = function(id) {
            Ouder.get({id : id}, function(result) {
                $scope.ouder = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('tiimspotApp:ouderUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ouder.id != null) {
                Ouder.update($scope.ouder, onSaveSuccess, onSaveError);
            } else {
                Ouder.save($scope.ouder, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
